#!/bin/bash

# Step 1: Run 'sudo make dist-swing'
clear

echo "Running 'sudo make dist-swing'..."
sudo make dist-swing
if [ $? -ne 0 ]; then
  echo "Error: 'sudo make dist-swing' failed. Exiting."
  exit 1
fi

# Step 2: Run './runrabbit swing'
echo "Running './runrabbit swing'..."
./runrabbit swing
if [ $? -ne 0 ]; then
  echo "Error: './runrabbit swing' failed. Exiting."
  exit 1
fi

echo "Script executed successfully."

