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
import { IRondleiding } from 'app/shared/model/rondleiding.model';
import { getEntity, updateEntity, createEntity, reset } from './rondleiding.reducer';

export const RondleidingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tentoonstellings = useAppSelector(state => state.tentoonstelling.entities);
  const rondleidingEntity = useAppSelector(state => state.rondleiding.entity);
  const loading = useAppSelector(state => state.rondleiding.loading);
  const updating = useAppSelector(state => state.rondleiding.updating);
  const updateSuccess = useAppSelector(state => state.rondleiding.updateSuccess);

  const handleClose = () => {
    navigate('/rondleiding');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTentoonstellings({}));
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
      ...rondleidingEntity,
      ...values,
      voorTentoonstelling: tentoonstellings.find(it => it.id.toString() === values.voorTentoonstelling?.toString()),
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
          ...rondleidingEntity,
          voorTentoonstelling: rondleidingEntity?.voorTentoonstelling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.rondleiding.home.createOrEditLabel" data-cy="RondleidingCreateUpdateHeading">
            Create or edit a Rondleiding
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="rondleiding-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Eindtijd" id="rondleiding-eindtijd" name="eindtijd" data-cy="eindtijd" type="text" />
              <ValidatedField label="Naam" id="rondleiding-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="rondleiding-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Starttijd" id="rondleiding-starttijd" name="starttijd" data-cy="starttijd" type="text" />
              <ValidatedField
                id="rondleiding-voorTentoonstelling"
                name="voorTentoonstelling"
                data-cy="voorTentoonstelling"
                label="Voor Tentoonstelling"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rondleiding" replace color="info">
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

export default RondleidingUpdate;
