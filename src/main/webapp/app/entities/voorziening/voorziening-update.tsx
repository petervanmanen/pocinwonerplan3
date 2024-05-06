import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVoorzieningsoort } from 'app/shared/model/voorzieningsoort.model';
import { getEntities as getVoorzieningsoorts } from 'app/entities/voorzieningsoort/voorzieningsoort.reducer';
import { IVoorziening } from 'app/shared/model/voorziening.model';
import { getEntity, updateEntity, createEntity, reset } from './voorziening.reducer';

export const VoorzieningUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const voorzieningsoorts = useAppSelector(state => state.voorzieningsoort.entities);
  const voorzieningEntity = useAppSelector(state => state.voorziening.entity);
  const loading = useAppSelector(state => state.voorziening.loading);
  const updating = useAppSelector(state => state.voorziening.updating);
  const updateSuccess = useAppSelector(state => state.voorziening.updateSuccess);

  const handleClose = () => {
    navigate('/voorziening');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVoorzieningsoorts({}));
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
      ...voorzieningEntity,
      ...values,
      valtbinnenVoorzieningsoort: voorzieningsoorts.find(it => it.id.toString() === values.valtbinnenVoorzieningsoort?.toString()),
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
          ...voorzieningEntity,
          valtbinnenVoorzieningsoort: voorzieningEntity?.valtbinnenVoorzieningsoort?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.voorziening.home.createOrEditLabel" data-cy="VoorzieningCreateUpdateHeading">
            Create or edit a Voorziening
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="voorziening-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aantalbeschikbaar"
                id="voorziening-aantalbeschikbaar"
                name="aantalbeschikbaar"
                data-cy="aantalbeschikbaar"
                type="text"
              />
              <ValidatedField label="Naam" id="voorziening-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="voorziening-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="voorziening-valtbinnenVoorzieningsoort"
                name="valtbinnenVoorzieningsoort"
                data-cy="valtbinnenVoorzieningsoort"
                label="Valtbinnen Voorzieningsoort"
                type="select"
              >
                <option value="" key="0" />
                {voorzieningsoorts
                  ? voorzieningsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/voorziening" replace color="info">
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

export default VoorzieningUpdate;
