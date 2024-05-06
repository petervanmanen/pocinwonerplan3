import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getOnderwerps } from 'app/entities/onderwerp/onderwerp.reducer';
import { IOnderwerp } from 'app/shared/model/onderwerp.model';
import { getEntity, updateEntity, createEntity, reset } from './onderwerp.reducer';

export const OnderwerpUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const onderwerps = useAppSelector(state => state.onderwerp.entities);
  const onderwerpEntity = useAppSelector(state => state.onderwerp.entity);
  const loading = useAppSelector(state => state.onderwerp.loading);
  const updating = useAppSelector(state => state.onderwerp.updating);
  const updateSuccess = useAppSelector(state => state.onderwerp.updateSuccess);

  const handleClose = () => {
    navigate('/onderwerp');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOnderwerps({}));
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
      ...onderwerpEntity,
      ...values,
      hoofdonderwerpOnderwerp: onderwerps.find(it => it.id.toString() === values.hoofdonderwerpOnderwerp?.toString()),
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
          ...onderwerpEntity,
          hoofdonderwerpOnderwerp: onderwerpEntity?.hoofdonderwerpOnderwerp?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.onderwerp.home.createOrEditLabel" data-cy="OnderwerpCreateUpdateHeading">
            Create or edit a Onderwerp
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="onderwerp-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                id="onderwerp-hoofdonderwerpOnderwerp"
                name="hoofdonderwerpOnderwerp"
                data-cy="hoofdonderwerpOnderwerp"
                label="Hoofdonderwerp Onderwerp"
                type="select"
              >
                <option value="" key="0" />
                {onderwerps
                  ? onderwerps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/onderwerp" replace color="info">
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

export default OnderwerpUpdate;
