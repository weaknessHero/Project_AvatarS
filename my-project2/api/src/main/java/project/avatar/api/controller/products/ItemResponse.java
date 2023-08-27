package project.avatar.api.controller.products;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemResponse {
    private List<Item> items;
}
