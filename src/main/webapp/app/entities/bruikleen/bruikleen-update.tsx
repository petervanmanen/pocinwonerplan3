import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITentoonstelling } from 'app/shared/model/tentoonstelling.model';
import { getEntities as getTentoonstellings } from 'app/entities/tentoonstelling/tentoonstelling.reducer';
import { ILener } from 'app/shared/model/lener.model';
import { getEntities as getLeners } from 'app/entities/lener/lener.reducer';
import { IBruikleen } from 'app/shared/model/bruikleen.model';
import { getEntity, updateEntity, createEntity, reset } from './bruikleen.reducer';

export const BruikleenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tentoonstellings = useAppSelector(state => state.tentoonstelling.entities);
  const leners = useAppSelector(state => state.lener.entities);
  const bruikleenEntity = useAppSelector(state => state.bruikleen.entity);
  const loading = useAppSelector(state => state.bruikleen.loading);
  const updating = useAppSelector(state => state.bruikleen.updating);
  const updateSuccess = useAppSelector(state => state.bruikleen.updateSuccess);

  const handleClose = () => {
    navigate('/bruikleen');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTentoonstellings({}));
    dispatch(getLeners({}));
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
      ...bruikleenEntity,
      ...values,
      isbedoeldvoorTentoonstellings: mapIdList(values.isbedoeldvoorTentoonstellings),
      isLeners: mapIdList(values.isLeners),
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
          ...bruikleenEntity,
          isbedoeldvoorTentoonstellings: bruikleenEntity?.isbedoeldvoorTentoonstellings?.map(e => e.id.toString()),
          isLeners: bruikleenEntity?.isLeners?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.bruikleen.home.createOrEditLabel" data-cy="BruikleenCreateUpdateHeading">
            Create or edit a Bruikleen
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="bruikleen-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aanvraagdoor" id="bruikleen-aanvraagdoor" name="aanvraagdoor" data-cy="aanvraagdoor" type="text" />
              <ValidatedField label="Datumaanvraag" id="bruikleen-datumaanvraag" name="datumaanvraag" data-cy="datumaanvraag" type="date" />
              <ValidatedField label="Datumeinde" id="bruikleen-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField label="Datumstart" id="bruikleen-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField
                label="Toestemmingdoor"
                id="bruikleen-toestemmingdoor"
                name="toestemmingdoor"
                data-cy="toestemmingdoor"
                type="text"
              />
              <ValidatedField
                label="Isbedoeldvoor Tentoonstelling"
                id="bruikleen-isbedoeldvoorTentoonstelling"
                data-cy="isbedoeldvoorTentoonstelling"
                type="select"
                multiple
                name="isbedoeldvoorTentoonstellings"
              >
                <option value="" key="0" />
                {tentoonstellings
                  ? tentoonstellings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField label="Is Lener" id="bruikleen-isLener" data-cy="isLener" type="select" multiple name="isLeners">
                <option value="" key="0" />
                {leners
                  ? leners.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bruikleen" replace color="info">
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

export default BruikleenUpdate;
