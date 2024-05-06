import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOmgevingswaarderegel } from 'app/shared/model/omgevingswaarderegel.model';
import { getEntities as getOmgevingswaarderegels } from 'app/entities/omgevingswaarderegel/omgevingswaarderegel.reducer';
import { IOmgevingsnorm } from 'app/shared/model/omgevingsnorm.model';
import { getEntity, updateEntity, createEntity, reset } from './omgevingsnorm.reducer';

export const OmgevingsnormUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const omgevingswaarderegels = useAppSelector(state => state.omgevingswaarderegel.entities);
  const omgevingsnormEntity = useAppSelector(state => state.omgevingsnorm.entity);
  const loading = useAppSelector(state => state.omgevingsnorm.loading);
  const updating = useAppSelector(state => state.omgevingsnorm.updating);
  const updateSuccess = useAppSelector(state => state.omgevingsnorm.updateSuccess);

  const handleClose = () => {
    navigate('/omgevingsnorm');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOmgevingswaarderegels({}));
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
      ...omgevingsnormEntity,
      ...values,
      beschrijftOmgevingswaarderegels: mapIdList(values.beschrijftOmgevingswaarderegels),
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
          ...omgevingsnormEntity,
          beschrijftOmgevingswaarderegels: omgevingsnormEntity?.beschrijftOmgevingswaarderegels?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.omgevingsnorm.home.createOrEditLabel" data-cy="OmgevingsnormCreateUpdateHeading">
            Create or edit a Omgevingsnorm
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
                <ValidatedField name="id" required readOnly id="omgevingsnorm-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Naam" id="omgevingsnorm-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omgevingsnormgroep"
                id="omgevingsnorm-omgevingsnormgroep"
                name="omgevingsnormgroep"
                data-cy="omgevingsnormgroep"
                type="text"
              />
              <ValidatedField
                label="Beschrijft Omgevingswaarderegel"
                id="omgevingsnorm-beschrijftOmgevingswaarderegel"
                data-cy="beschrijftOmgevingswaarderegel"
                type="select"
                multiple
                name="beschrijftOmgevingswaarderegels"
              >
                <option value="" key="0" />
                {omgevingswaarderegels
                  ? omgevingswaarderegels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/omgevingsnorm" replace color="info">
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

export default OmgevingsnormUpdate;
