package com.visma.fishing.mixins;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseMixIn {

    @JsonIgnore
    String id;

}
