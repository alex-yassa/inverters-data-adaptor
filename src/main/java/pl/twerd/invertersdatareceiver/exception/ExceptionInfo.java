package pl.twerd.invertersdatareceiver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ExceptionInfo {
	private String url;
	private String message;
}
