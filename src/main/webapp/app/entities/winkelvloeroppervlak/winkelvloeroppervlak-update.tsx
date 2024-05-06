import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWinkelvloeroppervlak } from 'app/shared/model/winkelvloeroppervlak.model';
import { getEntity, updateEntity, createEntity, reset } from './winkelvloeroppervlak.reducer';

export const WinkelvloeroppervlakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const winkelvloeroppervlakEntity = useAppSelector(state => state.winkelvloeroppervlak.entity);
  const loading = useAppSelector(state => state.winkelvloeroppervlak.loading);
  const updating = useAppSelector(state => state.winkelvloeroppervlak.updating);
  const updateSuccess = useAppSelector(state => state.winkelvloeroppervlak.updateSuccess);

  const handleClose = () => {
    navigate('/winkelvloeroppervlak');
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
      ...winkelvloeroppervlakEntity,
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
          ...winkelvloeroppervlakEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.winkelvloeroppervlak.home.createOrEditLabel" data-cy="WinkelvloeroppervlakCreateUpdateHeading">
            Create or edit a Winkelvloeroppervlak
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
                <ValidatedField name="id" required readOnly id="winkelvloeroppervlak-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aantalkassa"
                id="winkelvloeroppervlak-aantalkassa"
                name="aantalkassa"
                data-cy="aantalkassa"
                type="text"
              />
              <ValidatedField label="Bronwvo" id="winkelvloeroppervlak-bronwvo" name="bronwvo" data-cy="bronwvo" type="text" />
              <ValidatedField label="Leegstand" id="winkelvloeroppervlak-leegstand" name="leegstand" data-cy="leegstand" type="text" />
              <ValidatedField
                label="Winkelvloeroppervlakte"
                id="winkelvloeroppervlak-winkelvloeroppervlakte"
                name="winkelvloeroppervlakte"
                data-cy="winkelvloeroppervlakte"
                type="text"
              />
              <ValidatedField label="Wvoklasse" id="winkelvloeroppervlak-wvoklasse" name="wvoklasse" data-cy="wvoklasse" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/winkelvloeroppervlak" replace color="info">
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

export default WinkelvloeroppervlakUpdate;
