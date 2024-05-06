import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUitstroomredensoort } from 'app/shared/model/uitstroomredensoort.model';
import { getEntities as getUitstroomredensoorts } from 'app/entities/uitstroomredensoort/uitstroomredensoort.reducer';
import { IUitstroomreden } from 'app/shared/model/uitstroomreden.model';
import { getEntity, updateEntity, createEntity, reset } from './uitstroomreden.reducer';

export const UitstroomredenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const uitstroomredensoorts = useAppSelector(state => state.uitstroomredensoort.entities);
  const uitstroomredenEntity = useAppSelector(state => state.uitstroomreden.entity);
  const loading = useAppSelector(state => state.uitstroomreden.loading);
  const updating = useAppSelector(state => state.uitstroomreden.updating);
  const updateSuccess = useAppSelector(state => state.uitstroomreden.updateSuccess);

  const handleClose = () => {
    navigate('/uitstroomreden');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUitstroomredensoorts({}));
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
      ...uitstroomredenEntity,
      ...values,
      soortuitstroomredenUitstroomredensoort: uitstroomredensoorts.find(
        it => it.id.toString() === values.soortuitstroomredenUitstroomredensoort?.toString(),
      ),
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
          ...uitstroomredenEntity,
          soortuitstroomredenUitstroomredensoort: uitstroomredenEntity?.soortuitstroomredenUitstroomredensoort?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.uitstroomreden.home.createOrEditLabel" data-cy="UitstroomredenCreateUpdateHeading">
            Create or edit a Uitstroomreden
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
                <ValidatedField name="id" required readOnly id="uitstroomreden-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datum" id="uitstroomreden-datum" name="datum" data-cy="datum" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="uitstroomreden-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField
                id="uitstroomreden-soortuitstroomredenUitstroomredensoort"
                name="soortuitstroomredenUitstroomredensoort"
                data-cy="soortuitstroomredenUitstroomredensoort"
                label="Soortuitstroomreden Uitstroomredensoort"
                type="select"
              >
                <option value="" key="0" />
                {uitstroomredensoorts
                  ? uitstroomredensoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/uitstroomreden" replace color="info">
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

export default UitstroomredenUpdate;
