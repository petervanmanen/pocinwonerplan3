import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOmgevingsnorm } from 'app/shared/model/omgevingsnorm.model';
import { getEntities as getOmgevingsnorms } from 'app/entities/omgevingsnorm/omgevingsnorm.reducer';
import { IOmgevingswaarde } from 'app/shared/model/omgevingswaarde.model';
import { getEntities as getOmgevingswaardes } from 'app/entities/omgevingswaarde/omgevingswaarde.reducer';
import { IOmgevingswaarderegel } from 'app/shared/model/omgevingswaarderegel.model';
import { getEntity, updateEntity, createEntity, reset } from './omgevingswaarderegel.reducer';

export const OmgevingswaarderegelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const omgevingsnorms = useAppSelector(state => state.omgevingsnorm.entities);
  const omgevingswaardes = useAppSelector(state => state.omgevingswaarde.entities);
  const omgevingswaarderegelEntity = useAppSelector(state => state.omgevingswaarderegel.entity);
  const loading = useAppSelector(state => state.omgevingswaarderegel.loading);
  const updating = useAppSelector(state => state.omgevingswaarderegel.updating);
  const updateSuccess = useAppSelector(state => state.omgevingswaarderegel.updateSuccess);

  const handleClose = () => {
    navigate('/omgevingswaarderegel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOmgevingsnorms({}));
    dispatch(getOmgevingswaardes({}));
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
      ...omgevingswaarderegelEntity,
      ...values,
      beschrijftOmgevingsnorms: mapIdList(values.beschrijftOmgevingsnorms),
      beschrijftOmgevingswaardes: mapIdList(values.beschrijftOmgevingswaardes),
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
          ...omgevingswaarderegelEntity,
          beschrijftOmgevingsnorms: omgevingswaarderegelEntity?.beschrijftOmgevingsnorms?.map(e => e.id.toString()),
          beschrijftOmgevingswaardes: omgevingswaarderegelEntity?.beschrijftOmgevingswaardes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.omgevingswaarderegel.home.createOrEditLabel" data-cy="OmgevingswaarderegelCreateUpdateHeading">
            Create or edit a Omgevingswaarderegel
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
                <ValidatedField name="id" required readOnly id="omgevingswaarderegel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Groep" id="omgevingswaarderegel-groep" name="groep" data-cy="groep" type="text" />
              <ValidatedField label="Naam" id="omgevingswaarderegel-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Beschrijft Omgevingsnorm"
                id="omgevingswaarderegel-beschrijftOmgevingsnorm"
                data-cy="beschrijftOmgevingsnorm"
                type="select"
                multiple
                name="beschrijftOmgevingsnorms"
              >
                <option value="" key="0" />
                {omgevingsnorms
                  ? omgevingsnorms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Beschrijft Omgevingswaarde"
                id="omgevingswaarderegel-beschrijftOmgevingswaarde"
                data-cy="beschrijftOmgevingswaarde"
                type="select"
                multiple
                name="beschrijftOmgevingswaardes"
              >
                <option value="" key="0" />
                {omgevingswaardes
                  ? omgevingswaardes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/omgevingswaarderegel" replace color="info">
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

export default OmgevingswaarderegelUpdate;
