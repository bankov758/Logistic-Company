import { useState } from "react";

interface Option {
    label: string;
    value: string;
  }
  
  interface DropdownProps {
    options: Option[];
    selectedOption?: Option;
    onSelect: (option: Option) => void;
  }
  
  const Dropdown: React.FC<DropdownProps> = ({ options, selectedOption, onSelect }) => {
    const [isOpen, setIsOpen] = useState(false);
  
    const toggleDropdown = () => setIsOpen(!isOpen);
  
    return (
      <div className="dropdown">
        <button onClick={toggleDropdown}>{selectedOption?.label || "Select Option"}</button>
        {isOpen && (
          <ul className="dropdown-menu">
            {options.map((option) => (
              <li key={option.value} onClick={() => onSelect(option)}>
                {option.label}
              </li>
            ))}
          </ul>
        )}
      </div>
    );
  };
  
  export default Dropdown;