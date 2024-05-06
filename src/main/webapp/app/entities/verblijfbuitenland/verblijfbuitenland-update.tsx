import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerblijfbuitenland } from 'app/shared/model/verblijfbuitenland.model';
import { getEntity, updateEntity, createEntity, reset } from './verblijfbuitenland.reducer';

export const VerblijfbuitenlandUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verblijfbuitenlandEntity = useAppSelector(state => state.verblijfbuitenland.entity);
  const loading = useAppSelector(state => state.verblijfbuitenland.loading);
  const updating = useAppSelector(state => state.verblijfbuitenland.updating);
  const updateSuccess = useAppSelector(state => state.verblijfbuitenland.updateSuccess);

  const handleClose = () => {
    navigate('/verblijfbuitenland');
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
      ...verblijfbuitenlandEntity,
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
          ...verblijfbuitenlandEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verblijfbuitenland.home.createOrEditLabel" data-cy="VerblijfbuitenlandCreateUpdateHeading">
            Create or edit a Verblijfbuitenland
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
                <ValidatedField name="id" required readOnly id="verblijfbuitenland-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Adresregelbuitenland 1"
                id="verblijfbuitenland-adresregelbuitenland1"
                name="adresregelbuitenland1"
                data-cy="adresregelbuitenland1"
                type="text"
              />
              <ValidatedField
                label="Adresregelbuitenland 2"
                id="verblijfbuitenland-adresregelbuitenland2"
                name="adresregelbuitenland2"
                data-cy="adresregelbuitenland2"
                type="text"
              />
              <ValidatedField
                label="Adresregelbuitenland 3"
                id="verblijfbuitenland-adresregelbuitenland3"
                name="adresregelbuitenland3"
                data-cy="adresregelbuitenland3"
                type="text"
              />
              <ValidatedField
                label="Adresregelbuitenland 4"
                id="verblijfbuitenland-adresregelbuitenland4"
                name="adresregelbuitenland4"
                data-cy="adresregelbuitenland4"
                type="text"
              />
              <ValidatedField
                label="Adresregelbuitenland 5"
                id="verblijfbuitenland-adresregelbuitenland5"
                name="adresregelbuitenland5"
                data-cy="adresregelbuitenland5"
                type="text"
              />
              <ValidatedField
                label="Adresregelbuitenland 6"
                id="verblijfbuitenland-adresregelbuitenland6"
                name="adresregelbuitenland6"
                data-cy="adresregelbuitenland6"
                type="text"
              />
              <ValidatedField
                label="Landofgebiedverblijfadres"
                id="verblijfbuitenland-landofgebiedverblijfadres"
                name="landofgebiedverblijfadres"
                data-cy="landofgebiedverblijfadres"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verblijfbuitenland" replace color="info">
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

export default VerblijfbuitenlandUpdate;
