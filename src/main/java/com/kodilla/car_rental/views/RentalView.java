package com.kodilla.car_rental.views;

import com.kodilla.car_rental.client.RentalClient;
import com.kodilla.car_rental.dto.RentalDto;
import com.kodilla.car_rental.dto.RentalWithCarDto;
import com.kodilla.car_rental.dto.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@UIScope
@Component
public class RentalView extends VerticalLayout {

    private final Grid<RentalWithCarDto> rentalGrid = new Grid<>();
    private final RentalClient rentalClient;

    private final Dialog extendRentalDialog = new Dialog();
    private final IntegerField extension = new IntegerField("Extend by (days)");

    private final Dialog modifyRentalDialog = new Dialog();
    private final Binder<RentalDto> binderForModifyRental = new Binder<>();
    private final DatePicker modifyStartDate = new DatePicker("Rented from");
    private final DatePicker modifyEndDate = new DatePicker("Rented to");

    private UserDto loggedUserDto;
    private Long rentalId;
    private Long carId;

    @Autowired
    public RentalView(RentalClient rentalClient) {
        this.rentalClient = rentalClient;

        bindFields();

        VerticalLayout extendRentalDialogLayout = new VerticalLayout();
        extendRentalDialog.isCloseOnOutsideClick();
        extendRentalDialog.add(extendRentalDialogLayout);

        VerticalLayout modifyRentalDialogLayout = new VerticalLayout();
        Button confirmModifyRentalButton = createConfirmModifyRentalButton();
        modifyRentalDialogLayout.add(modifyStartDate, modifyEndDate, confirmModifyRentalButton);
        modifyRentalDialog.isCloseOnOutsideClick();
        modifyRentalDialog.add(modifyRentalDialogLayout);

        setColumns();

        rentalGrid.addComponentColumn(this::createCloseRentalButton);
        rentalGrid.addComponentColumn(this::createModifyRentalButton);

        add(rentalGrid);
    }

    public void refreshRentalsForAdmin() {
        loggedUserDto = null;
        List<RentalWithCarDto> rentals = rentalClient.getRentals();
        rentalGrid.setItems(rentals);
    }

    public void refreshRentalsForUser(UserDto userDto) {
        loggedUserDto = userDto;
        List<RentalWithCarDto> rentals = rentalClient.getRentalsByUserId(userDto.getId());
        rentalGrid.setItems(rentals);
    }

    private Button createCloseRentalButton(RentalWithCarDto rentalWithCarDto) {
        Dialog confirmCloseRentalDialog = createCloseRentalDialog(rentalWithCarDto);

        Button closeRentalButton = new Button("Close", event -> confirmCloseRentalDialog.open());

        closeRentalButton.setEnabled(loggedUserDto != null);
        return closeRentalButton;
    }

    private Dialog createCloseRentalDialog(RentalWithCarDto rentalWithCarDto) {
        Dialog confirmCloseRentalDialog = new Dialog();
        VerticalLayout confirmationLayout = new VerticalLayout();
        Button confirmCloseRentalButton = createConfirmCloseRentalButton(confirmCloseRentalDialog, rentalWithCarDto);
        Button cancelCloseRentalButton = createCancelConfirmationButton(confirmCloseRentalDialog);
        Label confirmationLabel = new Label("Are You sure about closing rental?");
        confirmationLayout.add(confirmationLabel, confirmCloseRentalButton, cancelCloseRentalButton);
        confirmCloseRentalDialog.add(confirmationLayout);
        return confirmCloseRentalDialog;
    }

    private Button createConfirmCloseRentalButton(Dialog dialog, RentalWithCarDto rentalWithCarDto) {
        return new Button("Confirm", event -> {
            rentalId = rentalWithCarDto.getId();
            closeRental(rentalId);
            dialog.close();
        });
    }

    private Button createCancelConfirmationButton(Dialog dialog) {
        return new Button("Cancel", event -> dialog.close());
    }

    private Button createModifyRentalButton(RentalWithCarDto rentalWithCarDto) {
        Button modifyRentalButton = new Button("Modify");
        modifyRentalButton.addClickListener(event -> {
            rentalId = rentalWithCarDto.getId();
            carId = rentalWithCarDto.getCarId();
            modifyRentalDialog.open();
        });
        modifyRentalButton.setEnabled(loggedUserDto != null);
        return modifyRentalButton;
    }

    private Button createConfirmModifyRentalButton() {
        return new Button("Confirm", event -> {
            RentalDto rentalDto = new RentalDto();
            binderForModifyRental.writeBeanIfValid(rentalDto);
            rentalDto.setId(rentalId);
            rentalDto.setCarId(carId);
            rentalDto.setUserId(loggedUserDto.getId());
            rentalClient.modifyRental(rentalDto);
            refreshRentalsForUser(loggedUserDto);
            modifyRentalDialog.close();
        });
    }

    private void closeRental(Long rentalId) {
        rentalClient.closeRental(rentalId);
        refreshRentalsForUser(loggedUserDto);
    }

    private void setColumns() {
        rentalGrid.addColumn(RentalWithCarDto::getId).setHeader("Id");
        rentalGrid.addColumn(RentalWithCarDto::getRentedFrom).setHeader("Start");
        rentalGrid.addColumn(RentalWithCarDto::getRentedTo).setHeader("End");
        rentalGrid.addColumn(RentalWithCarDto::getRentalCost).setHeader("Cost");
        rentalGrid.addColumn(RentalWithCarDto::getCarBrand).setHeader("Brand");
        rentalGrid.addColumn(RentalWithCarDto::getCarModel).setHeader("Model");
        rentalGrid.addColumn(RentalWithCarDto::getUserName).setHeader("Name");
        rentalGrid.addColumn(RentalWithCarDto::getUserLastName).setHeader("Surname");
    }

    private void bindFields() {

        binderForModifyRental.forField(modifyStartDate)
                .bind(RentalDto::getRentedFrom, RentalDto::setRentedFrom);
        binderForModifyRental.forField(modifyEndDate)
                .bind(RentalDto::getRentedTo, RentalDto::setRentedTo);
    }
}
