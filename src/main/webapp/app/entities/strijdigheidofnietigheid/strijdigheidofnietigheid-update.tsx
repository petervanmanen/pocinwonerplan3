import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStrijdigheidofnietigheid } from 'app/shared/model/strijdigheidofnietigheid.model';
import { getEntity, updateEntity, createEntity, reset } from './strijdigheidofnietigheid.reducer';

export const StrijdigheidofnietigheidUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const strijdigheidofnietigheidEntity = useAppSelector(state => state.strijdigheidofnietigheid.entity);
  const loading = useAppSelector(state => state.strijdigheidofnietigheid.loading);
  const updating = useAppSelector(state => state.strijdigheidofnietigheid.updating);
  const updateSuccess = useAppSelector(state => state.strijdigheidofnietigheid.updateSuccess);

  const handleClose = () => {
    navigate('/strijdigheidofnietigheid');
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
      ...strijdigheidofnietigheidEntity,
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
          ...strijdigheidofnietigheidEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.strijdigheidofnietigheid.home.createOrEditLabel" data-cy="StrijdigheidofnietigheidCreateUpdateHeading">
            Create or edit a Strijdigheidofnietigheid
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
                <ValidatedField name="id" required readOnly id="strijdigheidofnietigheid-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanduidingstrijdigheidnietigheid"
                id="strijdigheidofnietigheid-aanduidingstrijdigheidnietigheid"
                name="aanduidingstrijdigheidnietigheid"
                data-cy="aanduidingstrijdigheidnietigheid"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/strijdigheidofnietigheid" replace color="info">
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

export default StrijdigheidofnietigheidUpdate;
