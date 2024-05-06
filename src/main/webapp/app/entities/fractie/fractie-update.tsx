import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMilieustraat } from 'app/shared/model/milieustraat.model';
import { getEntities as getMilieustraats } from 'app/entities/milieustraat/milieustraat.reducer';
import { IStorting } from 'app/shared/model/storting.model';
import { getEntities as getStortings } from 'app/entities/storting/storting.reducer';
import { IFractie } from 'app/shared/model/fractie.model';
import { getEntity, updateEntity, createEntity, reset } from './fractie.reducer';

export const FractieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const milieustraats = useAppSelector(state => state.milieustraat.entities);
  const stortings = useAppSelector(state => state.storting.entities);
  const fractieEntity = useAppSelector(state => state.fractie.entity);
  const loading = useAppSelector(state => state.fractie.loading);
  const updating = useAppSelector(state => state.fractie.updating);
  const updateSuccess = useAppSelector(state => state.fractie.updateSuccess);

  const handleClose = () => {
    navigate('/fractie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMilieustraats({}));
    dispatch(getStortings({}));
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
      ...fractieEntity,
      ...values,
      inzamelpuntvanMilieustraats: mapIdList(values.inzamelpuntvanMilieustraats),
      fractieStortings: mapIdList(values.fractieStortings),
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
          ...fractieEntity,
          inzamelpuntvanMilieustraats: fractieEntity?.inzamelpuntvanMilieustraats?.map(e => e.id.toString()),
          fractieStortings: fractieEntity?.fractieStortings?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.fractie.home.createOrEditLabel" data-cy="FractieCreateUpdateHeading">
            Create or edit a Fractie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="fractie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Naam" id="fractie-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="fractie-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Inzamelpuntvan Milieustraat"
                id="fractie-inzamelpuntvanMilieustraat"
                data-cy="inzamelpuntvanMilieustraat"
                type="select"
                multiple
                name="inzamelpuntvanMilieustraats"
              >
                <option value="" key="0" />
                {milieustraats
                  ? milieustraats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Fractie Storting"
                id="fractie-fractieStorting"
                data-cy="fractieStorting"
                type="select"
                multiple
                name="fractieStortings"
              >
                <option value="" key="0" />
                {stortings
                  ? stortings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fractie" replace color="info">
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

export default FractieUpdate;
