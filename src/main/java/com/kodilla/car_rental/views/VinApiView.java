package com.kodilla.car_rental.views;

import com.kodilla.car_rental.client.api.vin.VinApiClient;
import com.kodilla.car_rental.dto.api.vin.VinApiDto;
import com.kodilla.car_rental.dto.api.vin.VinBodyDto;
import com.kodilla.car_rental.dto.api.vin.VinDecodedDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@UIScope
@Component
public class VinApiView extends VerticalLayout {

    private final Grid<VinBodyDto> grid = new Grid<>();
    private final VinApiClient vinApiClient;

    private final Dialog dialog = new Dialog();
    private final VinBodyDto vinBodyDto = new VinBodyDto();

    private final VinApiDto vinApiDto = new VinApiDto();
    private final Binder<VinApiDto> binder = new Binder<>();
    private final TextField textField = new TextField("Enter VIN below:");

    public VinApiView(VinApiClient vinApiClient) {
        this.vinApiClient = vinApiClient;

        bindFields();

        VerticalLayout dialogLayout = new VerticalLayout();
        Button confirmDecodeButton = createConfirmDecodeButton();
        dialogLayout.add(textField, confirmDecodeButton);

        dialog.isCloseOnOutsideClick();
        dialog.add(dialogLayout);

        setColumns();

        Button decodeButton = createDecodeButton();
        add(decodeButton, grid, dialog);

    }

    private Button createConfirmDecodeButton() {
        return new Button("Decode", event -> {
            binder.writeBeanIfValid(vinApiDto);
            VinDecodedDto vinDecodedDto = vinApiClient.decodeVinNumber(vinApiDto);
            List<VinBodyDto> resultsDtoList = vinDecodedDto.getResultsDtoList();
            grid.setItems(resultsDtoList);
            dialog.close();
            clearFields();
        });
    }

    private Button createDecodeButton() {
        return new Button("Decode VIN number", event -> dialog.open());
    }

    private void setColumns() {
        grid.addColumn(VinBodyDto::getManufacturer).setHeader("Brand");
        grid.addColumn(VinBodyDto::getModel).setHeader("Model");
        grid.addColumn(VinBodyDto::getProductYear).setHeader("Year");
        grid.addColumn(VinBodyDto::getFuelType).setHeader("Fuel");
    }

    private void bindFields() {
        binder.forField(textField)
                .bind(VinApiDto::getVinNumber, VinApiDto::setVinNumber);
    }

    private void clearFields() {
        textField.clear();
    }

    public void clearGrid() {
        List<VinBodyDto> emptyList = new ArrayList<>();
        grid.setItems(emptyList);
    }
}
