import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRaadslid } from 'app/shared/model/raadslid.model';
import { getEntities as getRaadslids } from 'app/entities/raadslid/raadslid.reducer';
import { IRaadscommissie } from 'app/shared/model/raadscommissie.model';
import { getEntity, updateEntity, createEntity, reset } from './raadscommissie.reducer';

export const RaadscommissieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const raadslids = useAppSelector(state => state.raadslid.entities);
  const raadscommissieEntity = useAppSelector(state => state.raadscommissie.entity);
  const loading = useAppSelector(state => state.raadscommissie.loading);
  const updating = useAppSelector(state => state.raadscommissie.updating);
  const updateSuccess = useAppSelector(state => state.raadscommissie.updateSuccess);

  const handleClose = () => {
    navigate('/raadscommissie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRaadslids({}));
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
      ...raadscommissieEntity,
      ...values,
      islidvanRaadslids: mapIdList(values.islidvanRaadslids),
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
          ...raadscommissieEntity,
          islidvanRaadslids: raadscommissieEntity?.islidvanRaadslids?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.raadscommissie.home.createOrEditLabel" data-cy="RaadscommissieCreateUpdateHeading">
            Create or edit a Raadscommissie
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
                <ValidatedField name="id" required readOnly id="raadscommissie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Naam" id="raadscommissie-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Islidvan Raadslid"
                id="raadscommissie-islidvanRaadslid"
                data-cy="islidvanRaadslid"
                type="select"
                multiple
                name="islidvanRaadslids"
              >
                <option value="" key="0" />
                {raadslids
                  ? raadslids.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/raadscommissie" replace color="info">
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

export default RaadscommissieUpdate;
