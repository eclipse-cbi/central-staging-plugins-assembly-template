# Central Staging Plugins Assembly Template

This repository provides an assembly template for maven project in combination with the Central Staging Plugins, help to generate a deployment bundle compatible with the [Sonatype Central Portal](https://central.sonatype.org/publish/publish-portal-upload/).

- [Central Staging Plugins Assembly Template](#central-staging-plugins-assembly-template)
  - [Features](#features)
  - [Project Structure](#project-structure)
  - [Requirements](#requirements)
  - [Quick Start](#quick-start)
    - [1. Clone and Customize](#1-clone-and-customize)
    - [2. Update Project Information](#2-update-project-information)
    - [3. Build the Project](#3-build-the-project)
  - [Publishing to Central Portal](#publishing-to-central-portal)
    - [Bundle Upload Method](#bundle-upload-method)
    - [Bundle Contents](#bundle-contents)
  - [Troubleshooting](#troubleshooting)
    - [Validation](#validation)
  - [Resources](#resources)
  - [License](#license)
  - [Contributing](#contributing)


## Features

- **Complete Maven Configuration**: Pre-configured POM with all required metadata for Maven Central
- **Assembly Configuration**: Custom assembly descriptor for creating Central Portal compatible bundles
- **Code Signing**: GPG signing configuration for artifact authentication
- **Documentation**: Automated javadoc and sources jar generation
- **Checksums**: SHA1 and MD5 checksum generation for all artifacts
- **Validation**: Example Java classes and unit tests

## Project Structure

```
├── pom.xml                           # Main Maven configuration
├── src/
│   ├── assembly/
│   │   └── full.xml                  # Assembly descriptor for bundle creation
│   ├── main/java/                    # Source code
│   └── test/java/                    # Unit tests
├── .github/
│   └── workflows/
│       ├── build.yml                 # Build and test workflow
│       └── publish-to-central.yml    # Publishing workflow
└── target/                           # Build artifacts and generated bundles
```

## Requirements

Before using this template, ensure you have:

1. **Java 17+**: Required for building the project
2. **Maven 3.9+**: For project management and building
3. **GPG Key**: For signing artifacts (see [Central Portal Requirements](https://central.sonatype.org/publish/requirements/))
4. **Central Portal Account** (Optional): Register at [Central Portal](https://central.sonatype.org/)

## Quick Start

### 1. Clone and Customize

```bash
git clone https://github.com/eclipse-cbi/central-staging-plugins-assembly-template.git
cd central-staging-plugins-assembly-template
```

### 2. Update Project Information

Edit the `pom.xml` file to match your project:

- `groupId`: Your organization's group ID
- `artifactId`: Your project's artifact ID
- `version`: Your project version
- `name` and `description`: Project metadata
- `url`: Project website/repository URL
- `licenses`: Project license information
- `developers`: Developer information
- `scm`: Source control management URLs


### 3. Build the Project

```bash
mvn clean install
```

This will generate:
- Main JAR file
- Sources JAR
- Javadoc JAR
- GPG signatures (.asc files)
- Checksums (MD5 and SHA1)
- Deployment bundle (ZIP file)

## Publishing to Central Portal

### Bundle Upload Method

This template is specifically designed for the [bundle upload method](https://central.sonatype.org/publish/publish-portal-upload/) to the Central Portal:

1. **Build the bundle**:
   ```bash
   mvn clean install
   ```

2. **Locate the bundle**:
   The generated bundle will be in `target/` with the naming pattern:
   ```
   {artifactId}-{version}-bin.zip
   ```

3. **Upload to Central Portal**:
   
   **Option A: Web Interface**
   - Visit [Central Portal](https://central.sonatype.org/)
   - Navigate to "Upload Bundle"
   - Upload your generated ZIP file
   - Follow the validation and publishing workflow

   **Option B: Command Line**
   ```bash
   mvn central-staging-plugins:rc-upload \
     -Dcentral.automaticPublishing=true \
     -Dcentral.artifactFile=./target/<my_bundle>-bin.zip \
     -Dcentral.bearerToken="XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
   ```
   
   Replace `<my_bundle>` with your actual bundle name and provide your Central Portal bearer token.

   **Option C: GitHub Actions (Recommended)**
   
   This template includes GitHub Actions workflows for automated publishing:
   
   - **Build Workflow** (`.github/workflows/build.yml`): Runs on every push/PR to validate the project
   - **Publish Workflow** (`.github/workflows/publish-to-central.yml`): Publishes to Central Portal on version tags
   
   To use GitHub Actions publishing:
   
   1. **Configure GitHub Secrets**:
      - `GPG_PRIVATE_KEY`: Your GPG private key (base64 encoded)
      - `GPG_PASSPHRASE`: Your GPG key passphrase
      - `CENTRAL_SONATYPE_TOKEN_USERNAME`: Your Central Portal username (optional)
      - `CENTRAL_SONATYPE_TOKEN_PASSWORD`: Your Central Portal password (optional)
      - `CENTRAL_BEARER_TOKEN`: Your Central Portal bearer token
   
   2. **Create a release**:
      - Go to your GitHub repository
      - Navigate to "Releases" and click "Create a new release"
      - Create a tag (e.g., `v1.0.0`) and publish the release
      - The workflow will automatically trigger
   
   3. **Monitor the workflow**: Check the Actions tab in your GitHub repository

### Bundle Contents

The generated bundle includes all required artifacts as specified in the [Central Portal requirements](https://central.sonatype.org/publish/requirements/):

- **Main JAR**: Your compiled application
- **Sources JAR**: Source code archive
- **Javadoc JAR**: Generated documentation
- **POM file**: Project metadata
- **GPG Signatures**: `.asc` files for all artifacts
- **Checksums**: MD5 and SHA1 hashes for verification

## Troubleshooting

### Validation

Before uploading, verify your bundle contains:

```bash
unzip -l target/{artifactId}-{version}-bin.zip
```

Expected structure:

```shell
`-- com
    `-- sonatype
        `-- central
            `-- example
                `-- example_java_project
                    `-- 0.1.0
                        |-- example_java_project-0.1.0-javadoc.jar
                        |-- example_java_project-0.1.0-javadoc.jar.asc
                        |-- example_java_project-0.1.0-javadoc.jar.md5
                        |-- example_java_project-0.1.0-javadoc.jar.sha1
                        |-- example_java_project-0.1.0-sources.jar
                        |-- example_java_project-0.1.0-sources.jar.asc
                        |-- example_java_project-0.1.0-sources.jar.md5
                        |-- example_java_project-0.1.0-sources.jar.sha1
                        |-- example_java_project-0.1.0.jar
                        |-- example_java_project-0.1.0.jar.asc
                        |-- example_java_project-0.1.0.jar.md5
                        |-- example_java_project-0.1.0.jar.sha1
                        |-- example_java_project-0.1.0.pom
                        |-- example_java_project-0.1.0.pom.asc
                        |-- example_java_project-0.1.0.pom.md5
                        `-- example_java_project-0.1.0.pom.sha1
```

see: https://central.sonatype.org/publish/publish-portal-upload/

## Resources

- [Central Portal Documentation](https://central.sonatype.org/publish/publish-portal-upload/)
- [Maven Central Requirements](https://central.sonatype.org/publish/requirements/)
- [Central Portal Publishing Guide](https://central.sonatype.org/publish/)
- [GPG Signing Guide](https://central.sonatype.org/publish/requirements/gpg/)

## License

Eclipse Public License v.2.0 ([LICENSE](LICENSE))

## Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on how to contribute to this project.
