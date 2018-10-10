package com.anglo.controleescolar.repository.entity;

import java.io.Serializable;

public interface Entity extends Serializable {
	default String getDefaultOrdinationField() { return null; };
}
