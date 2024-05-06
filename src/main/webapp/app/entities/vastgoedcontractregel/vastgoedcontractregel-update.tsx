import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVastgoedcontractregel } from 'app/shared/model/vastgoedcontractregel.model';
import { getEntity, updateEntity, createEntity, reset } from './vastgoedcontractregel.reducer';

export const VastgoedcontractregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vastgoedcontractregelEntity = useAppSelector(state => state.vastgoedcontractregel.entity);
  const loading = useAppSelector(state => state.vastgoedcontractregel.loading);
  const updating = useAppSelector(state => state.vastgoedcontractregel.updating);
  const updateSuccess = useAppSelector(state => state.vastgoedcontractregel.updateSuccess);

  const handleClose = () => {
    navigate('/vastgoedcontractregel');
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
      ...vastgoedcontractregelEntity,
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
          ...vastgoedcontractregelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vastgoedcontractregel.home.createOrEditLabel" data-cy="VastgoedcontractregelCreateUpdateHeading">
            Create or edit a Vastgoedcontractregel
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
                <ValidatedField name="id" required readOnly id="vastgoedcontractregel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bedrag" id="vastgoedcontractregel-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Datumeinde" id="vastgoedcontractregel-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="vastgoedcontractregel-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Frequentie" id="vastgoedcontractregel-frequentie" name="frequentie" data-cy="frequentie" type="text" />
              <ValidatedField
                label="Identificatie"
                id="vastgoedcontractregel-identificatie"
                name="identificatie"
                data-cy="identificatie"
                type="text"
              />
              <ValidatedField
                label="Omschrijving"
                id="vastgoedcontractregel-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Status" id="vastgoedcontractregel-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Type" id="vastgoedcontractregel-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vastgoedcontractregel" replace color="info">
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

export default VastgoedcontractregelUpdate;
