import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IExamen } from 'app/shared/model/examen.model';
import { getEntities as getExamen } from 'app/entities/examen/examen.reducer';
import { IExamenonderdeel } from 'app/shared/model/examenonderdeel.model';
import { getEntity, updateEntity, createEntity, reset } from './examenonderdeel.reducer';

export const ExamenonderdeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const examen = useAppSelector(state => state.examen.entities);
  const examenonderdeelEntity = useAppSelector(state => state.examenonderdeel.entity);
  const loading = useAppSelector(state => state.examenonderdeel.loading);
  const updating = useAppSelector(state => state.examenonderdeel.updating);
  const updateSuccess = useAppSelector(state => state.examenonderdeel.updateSuccess);

  const handleClose = () => {
    navigate('/examenonderdeel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getExamen({}));
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
      ...examenonderdeelEntity,
      ...values,
      emptyExamen: examen.find(it => it.id.toString() === values.emptyExamen?.toString()),
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
          ...examenonderdeelEntity,
          emptyExamen: examenonderdeelEntity?.emptyExamen?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.examenonderdeel.home.createOrEditLabel" data-cy="ExamenonderdeelCreateUpdateHeading">
            Create or edit a Examenonderdeel
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
                <ValidatedField name="id" required readOnly id="examenonderdeel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField id="examenonderdeel-emptyExamen" name="emptyExamen" data-cy="emptyExamen" label="Empty Examen" type="select">
                <option value="" key="0" />
                {examen
                  ? examen.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/examenonderdeel" replace color="info">
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

export default ExamenonderdeelUpdate;
