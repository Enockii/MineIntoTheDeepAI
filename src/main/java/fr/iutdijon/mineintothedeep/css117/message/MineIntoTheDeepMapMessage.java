package fr.iutdijon.mineintothedeep.css117.message;

import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepMap;
import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepMapCell;
import fr.iutdijon.mineintothedeep.css117.map.MineIntoTheDeepOreType;

import java.util.List;
import java.util.stream.Stream;

public class MineIntoTheDeepMapMessage extends MineIntoTheDeepMessage<MineIntoTheDeepMap> {
    public MineIntoTheDeepMapMessage() {
        super(MineIntoTheDeepMessages.CLIENT_MAP);
    }

    @Override
    public MineIntoTheDeepResponse<MineIntoTheDeepMap> parse(String[] responses) {
        List<MineIntoTheDeepMapCell> cells = Stream.of(responses).filter(response -> response != null && !response.isEmpty()).map(rawCell -> {
            String[] parameters = rawCell.split(";");
            if (parameters.length != 4)
                throw new IllegalArgumentException("Invalid cell: " + rawCell);

            try {
                int depth = Integer.parseInt(parameters[0]);
                int oreAmount = Integer.parseInt(parameters[1]);
                MineIntoTheDeepOreType oreType = MineIntoTheDeepOreType.fromString(parameters[2]);
                int owner = Integer.parseInt(parameters[3]);

                return new MineIntoTheDeepMapCell(depth, oreAmount, oreType, owner);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid cell: " + rawCell);
            }
        }).toList();

        return new MineIntoTheDeepResponse<>(new MineIntoTheDeepMap(cells), null);
    }
}
