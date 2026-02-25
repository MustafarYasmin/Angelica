package net.coderbot.iris.shaderpack;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector3i;

import java.util.Optional;

public class ComputeSource {
	@Getter private final String name;
	private final String source;
	@Getter private final IndirectPointer indirectPointer;
	@Getter @Setter private Vector3i workGroups;
	@Getter @Setter private Vector2f workGroupRelative;

    public ComputeSource(String name, String source, ShaderProperties properties) {
		this.name = name;
		this.source = source;
		this.indirectPointer = properties != null ? properties.getIndirectPointers().get(name) : null;
	}

    public Optional<String> getSource() {
		return Optional.ofNullable(source);
	}

    public boolean isValid() {
		return source != null;
	}

}
