import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAcademischetitel } from 'app/shared/model/academischetitel.model';
import { getEntity, updateEntity, createEntity, reset } from './academischetitel.reducer';

export const AcademischetitelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const academischetitelEntity = useAppSelector(state => state.academischetitel.entity);
  const loading = useAppSelector(state => state.academischetitel.loading);
  const updating = useAppSelector(state => state.academischetitel.updating);
  const updateSuccess = useAppSelector(state => state.academischetitel.updateSuccess);

  const handleClose = () => {
    navigate('/academischetitel');
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
      ...academischetitelEntity,
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
          ...academischetitelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.academischetitel.home.createOrEditLabel" data-cy="AcademischetitelCreateUpdateHeading">
            Create or edit a Academischetitel
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
                <ValidatedField name="id" required readOnly id="academischetitel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Codeacademischetitel"
                id="academischetitel-codeacademischetitel"
                name="codeacademischetitel"
                data-cy="codeacademischetitel"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheidtitel"
                id="academischetitel-datumbegingeldigheidtitel"
                name="datumbegingeldigheidtitel"
                data-cy="datumbegingeldigheidtitel"
                type="text"
              />
              <ValidatedField
                label="Datumeindegeldigheidtitel"
                id="academischetitel-datumeindegeldigheidtitel"
                name="datumeindegeldigheidtitel"
                data-cy="datumeindegeldigheidtitel"
                type="text"
              />
              <ValidatedField
                label="Omschrijvingacademischetitel"
                id="academischetitel-omschrijvingacademischetitel"
                name="omschrijvingacademischetitel"
                data-cy="omschrijvingacademischetitel"
                type="text"
              />
              <ValidatedField
                label="Positieacademischetiteltovnaam"
                id="academischetitel-positieacademischetiteltovnaam"
                name="positieacademischetiteltovnaam"
                data-cy="positieacademischetiteltovnaam"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/academischetitel" replace color="info">
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

export default AcademischetitelUpdate;
