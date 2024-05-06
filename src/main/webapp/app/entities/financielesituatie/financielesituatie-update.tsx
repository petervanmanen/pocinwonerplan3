import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFinancielesituatie } from 'app/shared/model/financielesituatie.model';
import { getEntity, updateEntity, createEntity, reset } from './financielesituatie.reducer';

export const FinancielesituatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const financielesituatieEntity = useAppSelector(state => state.financielesituatie.entity);
  const loading = useAppSelector(state => state.financielesituatie.loading);
  const updating = useAppSelector(state => state.financielesituatie.updating);
  const updateSuccess = useAppSelector(state => state.financielesituatie.updateSuccess);

  const handleClose = () => {
    navigate('/financielesituatie');
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
      ...financielesituatieEntity,
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
          ...financielesituatieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.financielesituatie.home.createOrEditLabel" data-cy="FinancielesituatieCreateUpdateHeading">
            Create or edit a Financielesituatie
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
                <ValidatedField name="id" required readOnly id="financielesituatie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumvastgesteld"
                id="financielesituatie-datumvastgesteld"
                name="datumvastgesteld"
                data-cy="datumvastgesteld"
                type="text"
              />
              <ValidatedField label="Schuld" id="financielesituatie-schuld" name="schuld" data-cy="schuld" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/financielesituatie" replace color="info">
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

export default FinancielesituatieUpdate;
