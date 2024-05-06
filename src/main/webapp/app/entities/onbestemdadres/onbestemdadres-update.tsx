import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOnbestemdadres } from 'app/shared/model/onbestemdadres.model';
import { getEntity, updateEntity, createEntity, reset } from './onbestemdadres.reducer';

export const OnbestemdadresUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const onbestemdadresEntity = useAppSelector(state => state.onbestemdadres.entity);
  const loading = useAppSelector(state => state.onbestemdadres.loading);
  const updating = useAppSelector(state => state.onbestemdadres.updating);
  const updateSuccess = useAppSelector(state => state.onbestemdadres.updateSuccess);

  const handleClose = () => {
    navigate('/onbestemdadres');
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
      ...onbestemdadresEntity,
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
          ...onbestemdadresEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.onbestemdadres.home.createOrEditLabel" data-cy="OnbestemdadresCreateUpdateHeading">
            Create or edit a Onbestemdadres
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
                <ValidatedField name="id" required readOnly id="onbestemdadres-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Huisletter" id="onbestemdadres-huisletter" name="huisletter" data-cy="huisletter" type="text" />
              <ValidatedField label="Huisnummer" id="onbestemdadres-huisnummer" name="huisnummer" data-cy="huisnummer" type="text" />
              <ValidatedField
                label="Huisnummertoevoeging"
                id="onbestemdadres-huisnummertoevoeging"
                name="huisnummertoevoeging"
                data-cy="huisnummertoevoeging"
                type="text"
              />
              <ValidatedField label="Postcode" id="onbestemdadres-postcode" name="postcode" data-cy="postcode" type="text" />
              <ValidatedField label="Straatnaam" id="onbestemdadres-straatnaam" name="straatnaam" data-cy="straatnaam" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/onbestemdadres" replace color="info">
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

export default OnbestemdadresUpdate;
