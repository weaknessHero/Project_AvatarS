package project.avatar.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResult<T> {
    private boolean success;
    private String message;
    private List<T> data;

    public ListResult(boolean success, String message, List<T> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getter and Setter methods...
}

