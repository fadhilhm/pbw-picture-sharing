package com.tubes.picturesharing.Tag;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@NoArgsConstructor 
@Data 
public class Tag {
    @NotNull
    private  int id;

    @NotNull
    @Size(max = 25)
    private String name;
    
    @NotNull
    private Boolean active;
}
