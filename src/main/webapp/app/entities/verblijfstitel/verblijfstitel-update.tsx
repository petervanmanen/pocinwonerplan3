import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerblijfstitel } from 'app/shared/model/verblijfstitel.model';
import { getEntity, updateEntity, createEntity, reset } from './verblijfstitel.reducer';

export const VerblijfstitelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verblijfstitelEntity = useAppSelector(state => state.verblijfstitel.entity);
  const loading = useAppSelector(state => state.verblijfstitel.loading);
  const updating = useAppSelector(state => state.verblijfstitel.updating);
  const updateSuccess = useAppSelector(state => state.verblijfstitel.updateSuccess);

  const handleClose = () => {
    navigate('/verblijfstitel');
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
      ...verblijfstitelEntity,
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
          ...verblijfstitelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verblijfstitel.home.createOrEditLabel" data-cy="VerblijfstitelCreateUpdateHeading">
            Create or edit a Verblijfstitel
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
                <ValidatedField name="id" required readOnly id="verblijfstitel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanduidingverblijfstitel"
                id="verblijfstitel-aanduidingverblijfstitel"
                name="aanduidingverblijfstitel"
                data-cy="aanduidingverblijfstitel"
                type="text"
              />
              <ValidatedField label="Datumbegin" id="verblijfstitel-datumbegin" name="datumbegin" data-cy="datumbegin" type="date" />
              <ValidatedField label="Datumeinde" id="verblijfstitel-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumopname" id="verblijfstitel-datumopname" name="datumopname" data-cy="datumopname" type="date" />
              <ValidatedField
                label="Datumbegingeldigheidverblijfstitel"
                id="verblijfstitel-datumbegingeldigheidverblijfstitel"
                name="datumbegingeldigheidverblijfstitel"
                data-cy="datumbegingeldigheidverblijfstitel"
                type="date"
              />
              <ValidatedField
                label="Verblijfstitelcode"
                id="verblijfstitel-verblijfstitelcode"
                name="verblijfstitelcode"
                data-cy="verblijfstitelcode"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verblijfstitel" replace color="info">
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

export default VerblijfstitelUpdate;
