import json
import random

# Sample data for the query
departure_address = "XXXXX"
arrival_address = "YYYY"
weight = 100
company_id = 1

# Define the table with username and password information
user_credentials = {
    (1, 1): {"username": "alex", "password": "123456789"},
    (1, 18): {"username": "DPS", "password": "1234567689"},
    (1, 20): {"username": "Tony", "password": "1234567689"},
    (1, 16): {"username": "pepi", "password": "1234567689"},
    (1, 17): {"username": "godzi", "password": "1234567689"},
    (1, 15): {"username": "kasier", "password": "1234567689"},
    (1, 19): {"username": "Lili", "password": "1234567689"},
    (3, 29): {"username": "user789", "password": "1234567890"},
    (3, 28): {"username": "user456", "password": "securepass789"},
    (3, 26): {"username": "Rusen", "password": "1234567689"},
    (3, 27): {"username": "user123", "password": "password123"},
    (4, 30): {"username": "user101", "password": "pass123456"},
    (4, 32): {"username": "user103", "password": "mypass123"},
    (4, 33): {"username": "user104", "password": "nikpass123"},
    (4, 31): {"username": "user102", "password": "987654321"},
    (4, 34): {"username": "user105", "password": "svet123456"},
    (4, 35): {"username": "user106", "password": "dragopass123"},
    (5, 40): {"username": "user111", "password": "asenpass123"},
    (5, 41): {"username": "user112", "password": "vazrazhdanepass123"},
    (5, 39): {"username": "user110", "password": "dimitpass123"},
    (5, 38): {"username": "user109", "password": "boykopass123"},
    (5, 42): {"username": "user113", "password": "svetlapass123"},
    (5, 36): {"username": "user107", "password": "elenapass123"},
    (5, 37): {"username": "user108", "password": "vasilpass123"},
    # Add more entries as needed
}


# Define a function to fetch employee and courier IDs based on company ID
def get_ids(company_id):
    company_data = {
        1: {  # DHL
            "office_employee_id": [1, 18, 20, 16, 17, 15, 19],
            "courier_id": [2]
        },
        3: {  # Speedy
            "office_employee_id": [26, 27, 28, 29],
            "courier_id": [43, 44]
        },
        4: {  # komp
            "office_employee_id": [30, 31, 32, 33, 34, 35],
            "courier_id": [45, 46]
        },
        5: {  # Express
            "office_employee_id": [36, 37, 38, 39, 40, 41, 42],
            "courier_id": [47, 48]
        }
    }

    return company_data.get(company_id, {"office_employee_id": [], "courier_id": []})


# Fetch IDs based on the provided company ID
ids = get_ids(company_id)

# Extracting first values for employee and courier IDs
employee_id = ids["office_employee_id"][0] if ids["office_employee_id"] else None
courier_id = ids["courier_id"][0] if ids["courier_id"] else None

# Generate random sender and receiver IDs between 1 and 20
sender_id = random.randint(1, 20)
receiver_id = random.randint(1, 20)

# Look up username and password based on sender ID and company ID
sender_username = None
sender_password = None

while sender_username is None or sender_password is None:
    credentials = user_credentials.get((company_id, sender_id))
    if credentials:
        sender_username = credentials["username"]
        sender_password = credentials["password"]
    else:
        sender_id = random.randint(1, 20)

# Constructing the JSON object
json_data = {
    "departureAddress": departure_address,
    "arrivalAddress": arrival_address,
    "weight": weight,
    "senderId": sender_id,
    "receiverId": receiver_id,
    "employeeId": employee_id,
    "sentDate": "2024-03-01T19:17:51",
    "courierId": courier_id,
    "companyId": company_id
}

# Convert the data to a JSON string with each variable on a new line
json_string = json.dumps(json_data, indent=4, separators=(',', ': '))

# Constructing the output string with each JSON variable on a new line
output_string = f"JSON data:\n{json_string}\nString data:\n{json_string}\nUser Info: (id: {sender_id}, username: {sender_username}, company_id: {company_id}, password: {sender_password})"

# Print the output
print(output_string)
#test