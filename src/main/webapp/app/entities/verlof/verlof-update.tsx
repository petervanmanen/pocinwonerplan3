import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerlofsoort } from 'app/shared/model/verlofsoort.model';
import { getEntities as getVerlofsoorts } from 'app/entities/verlofsoort/verlofsoort.reducer';
import { IWerknemer } from 'app/shared/model/werknemer.model';
import { getEntities as getWerknemers } from 'app/entities/werknemer/werknemer.reducer';
import { IVerlof } from 'app/shared/model/verlof.model';
import { getEntity, updateEntity, createEntity, reset } from './verlof.reducer';

export const VerlofUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verlofsoorts = useAppSelector(state => state.verlofsoort.entities);
  const werknemers = useAppSelector(state => state.werknemer.entities);
  const verlofEntity = useAppSelector(state => state.verlof.entity);
  const loading = useAppSelector(state => state.verlof.loading);
  const updating = useAppSelector(state => state.verlof.updating);
  const updateSuccess = useAppSelector(state => state.verlof.updateSuccess);

  const handleClose = () => {
    navigate('/verlof');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVerlofsoorts({}));
    dispatch(getWerknemers({}));
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
      ...verlofEntity,
      ...values,
      soortverlofVerlofsoort: verlofsoorts.find(it => it.id.toString() === values.soortverlofVerlofsoort?.toString()),
      heeftverlofWerknemer: werknemers.find(it => it.id.toString() === values.heeftverlofWerknemer?.toString()),
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
          ...verlofEntity,
          soortverlofVerlofsoort: verlofEntity?.soortverlofVerlofsoort?.id,
          heeftverlofWerknemer: verlofEntity?.heeftverlofWerknemer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verlof.home.createOrEditLabel" data-cy="VerlofCreateUpdateHeading">
            Create or edit a Verlof
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="verlof-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumaanvraag" id="verlof-datumaanvraag" name="datumaanvraag" data-cy="datumaanvraag" type="date" />
              <ValidatedField
                label="Datumtijdeinde"
                id="verlof-datumtijdeinde"
                name="datumtijdeinde"
                data-cy="datumtijdeinde"
                type="text"
              />
              <ValidatedField
                label="Datumtijdstart"
                id="verlof-datumtijdstart"
                name="datumtijdstart"
                data-cy="datumtijdstart"
                type="text"
              />
              <ValidatedField
                label="Datumtoekenning"
                id="verlof-datumtoekenning"
                name="datumtoekenning"
                data-cy="datumtoekenning"
                type="date"
              />
              <ValidatedField label="Goedgekeurd" id="verlof-goedgekeurd" name="goedgekeurd" data-cy="goedgekeurd" check type="checkbox" />
              <ValidatedField
                id="verlof-soortverlofVerlofsoort"
                name="soortverlofVerlofsoort"
                data-cy="soortverlofVerlofsoort"
                label="Soortverlof Verlofsoort"
                type="select"
              >
                <option value="" key="0" />
                {verlofsoorts
                  ? verlofsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="verlof-heeftverlofWerknemer"
                name="heeftverlofWerknemer"
                data-cy="heeftverlofWerknemer"
                label="Heeftverlof Werknemer"
                type="select"
              >
                <option value="" key="0" />
                {werknemers
                  ? werknemers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verlof" replace color="info">
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

export default VerlofUpdate;
