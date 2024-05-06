import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKadastraalperceel } from 'app/shared/model/kadastraalperceel.model';
import { getEntity, updateEntity, createEntity, reset } from './kadastraalperceel.reducer';

export const KadastraalperceelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kadastraalperceelEntity = useAppSelector(state => state.kadastraalperceel.entity);
  const loading = useAppSelector(state => state.kadastraalperceel.loading);
  const updating = useAppSelector(state => state.kadastraalperceel.updating);
  const updateSuccess = useAppSelector(state => state.kadastraalperceel.updateSuccess);

  const handleClose = () => {
    navigate('/kadastraalperceel');
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
      ...kadastraalperceelEntity,
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
          ...kadastraalperceelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kadastraalperceel.home.createOrEditLabel" data-cy="KadastraalperceelCreateUpdateHeading">
            Create or edit a Kadastraalperceel
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
                <ValidatedField name="id" required readOnly id="kadastraalperceel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanduidingsoortgrootte"
                id="kadastraalperceel-aanduidingsoortgrootte"
                name="aanduidingsoortgrootte"
                data-cy="aanduidingsoortgrootte"
                type="text"
              />
              <ValidatedField
                label="Begrenzingperceel"
                id="kadastraalperceel-begrenzingperceel"
                name="begrenzingperceel"
                data-cy="begrenzingperceel"
                type="text"
              />
              <ValidatedField
                label="Grootteperceel"
                id="kadastraalperceel-grootteperceel"
                name="grootteperceel"
                data-cy="grootteperceel"
                type="text"
              />
              <ValidatedField
                label="Indicatiedeelperceel"
                id="kadastraalperceel-indicatiedeelperceel"
                name="indicatiedeelperceel"
                data-cy="indicatiedeelperceel"
                type="text"
              />
              <ValidatedField
                label="Omschrijvingdeelperceel"
                id="kadastraalperceel-omschrijvingdeelperceel"
                name="omschrijvingdeelperceel"
                data-cy="omschrijvingdeelperceel"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Plaatscoordinatenperceel"
                id="kadastraalperceel-plaatscoordinatenperceel"
                name="plaatscoordinatenperceel"
                data-cy="plaatscoordinatenperceel"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kadastraalperceel" replace color="info">
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

export default KadastraalperceelUpdate;
