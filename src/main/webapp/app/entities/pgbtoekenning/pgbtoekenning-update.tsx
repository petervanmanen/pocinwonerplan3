import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPgbtoekenning } from 'app/shared/model/pgbtoekenning.model';
import { getEntity, updateEntity, createEntity, reset } from './pgbtoekenning.reducer';

export const PgbtoekenningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pgbtoekenningEntity = useAppSelector(state => state.pgbtoekenning.entity);
  const loading = useAppSelector(state => state.pgbtoekenning.loading);
  const updating = useAppSelector(state => state.pgbtoekenning.updating);
  const updateSuccess = useAppSelector(state => state.pgbtoekenning.updateSuccess);

  const handleClose = () => {
    navigate('/pgbtoekenning');
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
    if (values.budget !== undefined && typeof values.budget !== 'number') {
      values.budget = Number(values.budget);
    }

    const entity = {
      ...pgbtoekenningEntity,
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
          ...pgbtoekenningEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.pgbtoekenning.home.createOrEditLabel" data-cy="PgbtoekenningCreateUpdateHeading">
            Create or edit a Pgbtoekenning
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
                <ValidatedField name="id" required readOnly id="pgbtoekenning-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Budget" id="pgbtoekenning-budget" name="budget" data-cy="budget" type="text" />
              <ValidatedField label="Datumeinde" id="pgbtoekenning-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumtoekenning"
                id="pgbtoekenning-datumtoekenning"
                name="datumtoekenning"
                data-cy="datumtoekenning"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pgbtoekenning" replace color="info">
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

export default PgbtoekenningUpdate;
