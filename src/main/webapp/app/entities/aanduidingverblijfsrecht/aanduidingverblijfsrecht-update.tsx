import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAanduidingverblijfsrecht } from 'app/shared/model/aanduidingverblijfsrecht.model';
import { getEntity, updateEntity, createEntity, reset } from './aanduidingverblijfsrecht.reducer';

export const AanduidingverblijfsrechtUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aanduidingverblijfsrechtEntity = useAppSelector(state => state.aanduidingverblijfsrecht.entity);
  const loading = useAppSelector(state => state.aanduidingverblijfsrecht.loading);
  const updating = useAppSelector(state => state.aanduidingverblijfsrecht.updating);
  const updateSuccess = useAppSelector(state => state.aanduidingverblijfsrecht.updateSuccess);

  const handleClose = () => {
    navigate('/aanduidingverblijfsrecht');
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
      ...aanduidingverblijfsrechtEntity,
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
          ...aanduidingverblijfsrechtEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aanduidingverblijfsrecht.home.createOrEditLabel" data-cy="AanduidingverblijfsrechtCreateUpdateHeading">
            Create or edit a Aanduidingverblijfsrecht
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
                <ValidatedField name="id" required readOnly id="aanduidingverblijfsrecht-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumaanvanggeldigheidverblijfsrecht"
                id="aanduidingverblijfsrecht-datumaanvanggeldigheidverblijfsrecht"
                name="datumaanvanggeldigheidverblijfsrecht"
                data-cy="datumaanvanggeldigheidverblijfsrecht"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidverblijfsrecht"
                id="aanduidingverblijfsrecht-datumeindegeldigheidverblijfsrecht"
                name="datumeindegeldigheidverblijfsrecht"
                data-cy="datumeindegeldigheidverblijfsrecht"
                type="date"
              />
              <ValidatedField
                label="Verblijfsrechtnummer"
                id="aanduidingverblijfsrecht-verblijfsrechtnummer"
                name="verblijfsrechtnummer"
                data-cy="verblijfsrechtnummer"
                type="text"
              />
              <ValidatedField
                label="Verblijfsrechtomschrijving"
                id="aanduidingverblijfsrecht-verblijfsrechtomschrijving"
                name="verblijfsrechtomschrijving"
                data-cy="verblijfsrechtomschrijving"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aanduidingverblijfsrecht" replace color="info">
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

export default AanduidingverblijfsrechtUpdate;
