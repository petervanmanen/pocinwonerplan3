import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerhuurbaareenheid } from 'app/shared/model/verhuurbaareenheid.model';
import { getEntity, updateEntity, createEntity, reset } from './verhuurbaareenheid.reducer';

export const VerhuurbaareenheidUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verhuurbaareenheidEntity = useAppSelector(state => state.verhuurbaareenheid.entity);
  const loading = useAppSelector(state => state.verhuurbaareenheid.loading);
  const updating = useAppSelector(state => state.verhuurbaareenheid.updating);
  const updateSuccess = useAppSelector(state => state.verhuurbaareenheid.updateSuccess);

  const handleClose = () => {
    navigate('/verhuurbaareenheid');
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
      ...verhuurbaareenheidEntity,
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
          ...verhuurbaareenheidEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verhuurbaareenheid.home.createOrEditLabel" data-cy="VerhuurbaareenheidCreateUpdateHeading">
            Create or edit a Verhuurbaareenheid
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="verhuurbaareenheid-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Adres" id="verhuurbaareenheid-adres" name="adres" data-cy="adres" type="text" />
              <ValidatedField label="Afmeting" id="verhuurbaareenheid-afmeting" name="afmeting" data-cy="afmeting" type="text" />
              <ValidatedField label="Bezetting" id="verhuurbaareenheid-bezetting" name="bezetting" data-cy="bezetting" type="text" />
              <ValidatedField label="Datumeinde" id="verhuurbaareenheid-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="verhuurbaareenheid-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                label="Datumwerkelijkbegin"
                id="verhuurbaareenheid-datumwerkelijkbegin"
                name="datumwerkelijkbegin"
                data-cy="datumwerkelijkbegin"
                type="date"
              />
              <ValidatedField
                label="Datumwerkelijkeinde"
                id="verhuurbaareenheid-datumwerkelijkeinde"
                name="datumwerkelijkeinde"
                data-cy="datumwerkelijkeinde"
                type="date"
              />
              <ValidatedField label="Huurprijs" id="verhuurbaareenheid-huurprijs" name="huurprijs" data-cy="huurprijs" type="text" />
              <ValidatedField
                label="Identificatie"
                id="verhuurbaareenheid-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField label="Naam" id="verhuurbaareenheid-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Nettoomtrek"
                id="verhuurbaareenheid-nettoomtrek"
                name="nettoomtrek"
                data-cy="nettoomtrek"
                type="text"
              />
              <ValidatedField
                label="Nettooppervlak"
                id="verhuurbaareenheid-nettooppervlak"
                name="nettooppervlak"
                data-cy="nettooppervlak"
                type="text"
              />
              <ValidatedField
                label="Opmerkingen"
                id="verhuurbaareenheid-opmerkingen"
                name="opmerkingen"
                data-cy="opmerkingen"
                type="text"
              />
              <ValidatedField label="Type" id="verhuurbaareenheid-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verhuurbaareenheid" replace color="info">
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

export default VerhuurbaareenheidUpdate;
