import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOverigbenoemdterrein } from 'app/shared/model/overigbenoemdterrein.model';
import { getEntity, updateEntity, createEntity, reset } from './overigbenoemdterrein.reducer';

export const OverigbenoemdterreinUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const overigbenoemdterreinEntity = useAppSelector(state => state.overigbenoemdterrein.entity);
  const loading = useAppSelector(state => state.overigbenoemdterrein.loading);
  const updating = useAppSelector(state => state.overigbenoemdterrein.updating);
  const updateSuccess = useAppSelector(state => state.overigbenoemdterrein.updateSuccess);

  const handleClose = () => {
    navigate('/overigbenoemdterrein');
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
      ...overigbenoemdterreinEntity,
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
          ...overigbenoemdterreinEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.overigbenoemdterrein.home.createOrEditLabel" data-cy="OverigbenoemdterreinCreateUpdateHeading">
            Create or edit a Overigbenoemdterrein
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
                <ValidatedField name="id" required readOnly id="overigbenoemdterrein-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Gebruiksdoeloverigbenoemdterrein"
                id="overigbenoemdterrein-gebruiksdoeloverigbenoemdterrein"
                name="gebruiksdoeloverigbenoemdterrein"
                data-cy="gebruiksdoeloverigbenoemdterrein"
                type="text"
              />
              <ValidatedField
                label="Overigbenoemdterreinidentificatie"
                id="overigbenoemdterrein-overigbenoemdterreinidentificatie"
                name="overigbenoemdterreinidentificatie"
                data-cy="overigbenoemdterreinidentificatie"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/overigbenoemdterrein" replace color="info">
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

export default OverigbenoemdterreinUpdate;
