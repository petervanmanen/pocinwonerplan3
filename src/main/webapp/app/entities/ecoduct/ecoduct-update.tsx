import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEcoduct } from 'app/shared/model/ecoduct.model';
import { getEntity, updateEntity, createEntity, reset } from './ecoduct.reducer';

export const EcoductUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ecoductEntity = useAppSelector(state => state.ecoduct.entity);
  const loading = useAppSelector(state => state.ecoduct.loading);
  const updating = useAppSelector(state => state.ecoduct.updating);
  const updateSuccess = useAppSelector(state => state.ecoduct.updateSuccess);

  const handleClose = () => {
    navigate('/ecoduct');
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
      ...ecoductEntity,
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
          ...ecoductEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.ecoduct.home.createOrEditLabel" data-cy="EcoductCreateUpdateHeading">
            Create or edit a Ecoduct
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="ecoduct-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aantaloverspanningen"
                id="ecoduct-aantaloverspanningen"
                name="aantaloverspanningen"
                data-cy="aantaloverspanningen"
                type="text"
              />
              <ValidatedField label="Draagvermogen" id="ecoduct-draagvermogen" name="draagvermogen" data-cy="draagvermogen" type="text" />
              <ValidatedField
                label="Maximaaltoelaatbaarvoertuiggewicht"
                id="ecoduct-maximaaltoelaatbaarvoertuiggewicht"
                name="maximaaltoelaatbaarvoertuiggewicht"
                data-cy="maximaaltoelaatbaarvoertuiggewicht"
                type="text"
              />
              <ValidatedField
                label="Maximaleasbelasting"
                id="ecoduct-maximaleasbelasting"
                name="maximaleasbelasting"
                data-cy="maximaleasbelasting"
                type="text"
              />
              <ValidatedField
                label="Maximaleoverspanning"
                id="ecoduct-maximaleoverspanning"
                name="maximaleoverspanning"
                data-cy="maximaleoverspanning"
                type="text"
              />
              <ValidatedField
                label="Overbruggingsobjectdoorrijopening"
                id="ecoduct-overbruggingsobjectdoorrijopening"
                name="overbruggingsobjectdoorrijopening"
                data-cy="overbruggingsobjectdoorrijopening"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField label="Type" id="ecoduct-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Zwaarstevoertuig"
                id="ecoduct-zwaarstevoertuig"
                name="zwaarstevoertuig"
                data-cy="zwaarstevoertuig"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ecoduct" replace color="info">
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

export default EcoductUpdate;
