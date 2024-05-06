import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISpeeltoestel } from 'app/shared/model/speeltoestel.model';
import { getEntity, updateEntity, createEntity, reset } from './speeltoestel.reducer';

export const SpeeltoestelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const speeltoestelEntity = useAppSelector(state => state.speeltoestel.entity);
  const loading = useAppSelector(state => state.speeltoestel.loading);
  const updating = useAppSelector(state => state.speeltoestel.updating);
  const updateSuccess = useAppSelector(state => state.speeltoestel.updateSuccess);

  const handleClose = () => {
    navigate('/speeltoestel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...speeltoestelEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...speeltoestelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.speeltoestel.home.createOrEditLabel" data-cy="SpeeltoestelCreateUpdateHeading">
            Create or edit a Speeltoestel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="speeltoestel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Catalogusprijs"
                id="speeltoestel-catalogusprijs"
                name="catalogusprijs"
                data-cy="catalogusprijs"
                type="text"
              />
              <ValidatedField
                label="Certificaat"
                id="speeltoestel-certificaat"
                name="certificaat"
                data-cy="certificaat"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Certificaatnummer"
                id="speeltoestel-certificaatnummer"
                name="certificaatnummer"
                data-cy="certificaatnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Certificeringsinstantie"
                id="speeltoestel-certificeringsinstantie"
                name="certificeringsinstantie"
                data-cy="certificeringsinstantie"
                type="text"
              />
              <ValidatedField
                label="Controlefrequentie"
                id="speeltoestel-controlefrequentie"
                name="controlefrequentie"
                data-cy="controlefrequentie"
                type="text"
              />
              <ValidatedField
                label="Datumcertificaat"
                id="speeltoestel-datumcertificaat"
                name="datumcertificaat"
                data-cy="datumcertificaat"
                type="date"
              />
              <ValidatedField
                label="Gemakkelijktoegankelijk"
                id="speeltoestel-gemakkelijktoegankelijk"
                name="gemakkelijktoegankelijk"
                data-cy="gemakkelijktoegankelijk"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Inspectievolgorde"
                id="speeltoestel-inspectievolgorde"
                name="inspectievolgorde"
                data-cy="inspectievolgorde"
                type="text"
              />
              <ValidatedField
                label="Installatiekosten"
                id="speeltoestel-installatiekosten"
                name="installatiekosten"
                data-cy="installatiekosten"
                type="text"
              />
              <ValidatedField label="Speelterrein" id="speeltoestel-speelterrein" name="speelterrein" data-cy="speelterrein" type="text" />
              <ValidatedField
                label="Speeltoesteltoestelonderdeel"
                id="speeltoestel-speeltoesteltoestelonderdeel"
                name="speeltoesteltoestelonderdeel"
                data-cy="speeltoesteltoestelonderdeel"
                type="text"
              />
              <ValidatedField
                label="Technischelevensduur"
                id="speeltoestel-technischelevensduur"
                name="technischelevensduur"
                data-cy="technischelevensduur"
                type="text"
              />
              <ValidatedField label="Toestelcode" id="speeltoestel-toestelcode" name="toestelcode" data-cy="toestelcode" type="text" />
              <ValidatedField label="Toestelgroep" id="speeltoestel-toestelgroep" name="toestelgroep" data-cy="toestelgroep" type="text" />
              <ValidatedField label="Toestelnaam" id="speeltoestel-toestelnaam" name="toestelnaam" data-cy="toestelnaam" type="text" />
              <ValidatedField label="Type" id="speeltoestel-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Typenummer" id="speeltoestel-typenummer" name="typenummer" data-cy="typenummer" type="text" />
              <ValidatedField label="Typeplus" id="speeltoestel-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <ValidatedField
                label="Typeplus 2"
                id="speeltoestel-typeplus2"
                name="typeplus2"
                data-cy="typeplus2"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Valruimtehoogte"
                id="speeltoestel-valruimtehoogte"
                name="valruimtehoogte"
                data-cy="valruimtehoogte"
                type="text"
              />
              <ValidatedField
                label="Valruimteomvang"
                id="speeltoestel-valruimteomvang"
                name="valruimteomvang"
                data-cy="valruimteomvang"
                type="text"
              />
              <ValidatedField
                label="Vrijevalhoogte"
                id="speeltoestel-vrijevalhoogte"
                name="vrijevalhoogte"
                data-cy="vrijevalhoogte"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/speeltoestel" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SpeeltoestelUpdate;
