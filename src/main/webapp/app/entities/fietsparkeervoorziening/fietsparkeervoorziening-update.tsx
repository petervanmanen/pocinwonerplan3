import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFietsparkeervoorziening } from 'app/shared/model/fietsparkeervoorziening.model';
import { getEntity, updateEntity, createEntity, reset } from './fietsparkeervoorziening.reducer';

export const FietsparkeervoorzieningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fietsparkeervoorzieningEntity = useAppSelector(state => state.fietsparkeervoorziening.entity);
  const loading = useAppSelector(state => state.fietsparkeervoorziening.loading);
  const updating = useAppSelector(state => state.fietsparkeervoorziening.updating);
  const updateSuccess = useAppSelector(state => state.fietsparkeervoorziening.updateSuccess);

  const handleClose = () => {
    navigate('/fietsparkeervoorziening');
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
      ...fietsparkeervoorzieningEntity,
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
          ...fietsparkeervoorzieningEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.fietsparkeervoorziening.home.createOrEditLabel" data-cy="FietsparkeervoorzieningCreateUpdateHeading">
            Create or edit a Fietsparkeervoorziening
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
                <ValidatedField name="id" required readOnly id="fietsparkeervoorziening-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aantalparkeerplaatsen"
                id="fietsparkeervoorziening-aantalparkeerplaatsen"
                name="aantalparkeerplaatsen"
                data-cy="aantalparkeerplaatsen"
                type="text"
              />
              <ValidatedField label="Type" id="fietsparkeervoorziening-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Typeplus" id="fietsparkeervoorziening-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fietsparkeervoorziening" replace color="info">
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

export default FietsparkeervoorzieningUpdate;
