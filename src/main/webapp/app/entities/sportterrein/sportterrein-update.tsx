import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISportterrein } from 'app/shared/model/sportterrein.model';
import { getEntity, updateEntity, createEntity, reset } from './sportterrein.reducer';

export const SportterreinUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sportterreinEntity = useAppSelector(state => state.sportterrein.entity);
  const loading = useAppSelector(state => state.sportterrein.loading);
  const updating = useAppSelector(state => state.sportterrein.updating);
  const updateSuccess = useAppSelector(state => state.sportterrein.updateSuccess);

  const handleClose = () => {
    navigate('/sportterrein');
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
      ...sportterreinEntity,
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
          ...sportterreinEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sportterrein.home.createOrEditLabel" data-cy="SportterreinCreateUpdateHeading">
            Create or edit a Sportterrein
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="sportterrein-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Drainage" id="sportterrein-drainage" name="drainage" data-cy="drainage" check type="checkbox" />
              <ValidatedField label="Gebruiksvorm" id="sportterrein-gebruiksvorm" name="gebruiksvorm" data-cy="gebruiksvorm" type="text" />
              <ValidatedField label="Sportcomplex" id="sportterrein-sportcomplex" name="sportcomplex" data-cy="sportcomplex" type="text" />
              <ValidatedField
                label="Sportterreintypesport"
                id="sportterrein-sportterreintypesport"
                name="sportterreintypesport"
                data-cy="sportterreintypesport"
                type="text"
              />
              <ValidatedField label="Type" id="sportterrein-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Typeplus" id="sportterrein-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <ValidatedField label="Veldnummer" id="sportterrein-veldnummer" name="veldnummer" data-cy="veldnummer" type="text" />
              <ValidatedField label="Verlicht" id="sportterrein-verlicht" name="verlicht" data-cy="verlicht" check type="checkbox" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sportterrein" replace color="info">
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

export default SportterreinUpdate;
