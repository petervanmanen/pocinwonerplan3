import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IToepasbareregel } from 'app/shared/model/toepasbareregel.model';
import { getEntity, updateEntity, createEntity, reset } from './toepasbareregel.reducer';

export const ToepasbareregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const toepasbareregelEntity = useAppSelector(state => state.toepasbareregel.entity);
  const loading = useAppSelector(state => state.toepasbareregel.loading);
  const updating = useAppSelector(state => state.toepasbareregel.updating);
  const updateSuccess = useAppSelector(state => state.toepasbareregel.updateSuccess);

  const handleClose = () => {
    navigate('/toepasbareregel');
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
      ...toepasbareregelEntity,
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
          ...toepasbareregelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.toepasbareregel.home.createOrEditLabel" data-cy="ToepasbareregelCreateUpdateHeading">
            Create or edit a Toepasbareregel
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
                <ValidatedField name="id" required readOnly id="toepasbareregel-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbegingeldigheid"
                id="toepasbareregel-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="toepasbareregel-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Domein" id="toepasbareregel-domein" name="domein" data-cy="domein" type="text" />
              <ValidatedField label="Naam" id="toepasbareregel-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="toepasbareregel-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                label="Soortaansluitpunt"
                id="toepasbareregel-soortaansluitpunt"
                name="soortaansluitpunt"
                data-cy="soortaansluitpunt"
                type="text"
              />
              <ValidatedField label="Toestemming" id="toepasbareregel-toestemming" name="toestemming" data-cy="toestemming" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/toepasbareregel" replace color="info">
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

export default ToepasbareregelUpdate;
