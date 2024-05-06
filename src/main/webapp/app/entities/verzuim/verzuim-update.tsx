import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVerzuimsoort } from 'app/shared/model/verzuimsoort.model';
import { getEntities as getVerzuimsoorts } from 'app/entities/verzuimsoort/verzuimsoort.reducer';
import { IWerknemer } from 'app/shared/model/werknemer.model';
import { getEntities as getWerknemers } from 'app/entities/werknemer/werknemer.reducer';
import { IVerzuim } from 'app/shared/model/verzuim.model';
import { getEntity, updateEntity, createEntity, reset } from './verzuim.reducer';

export const VerzuimUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const verzuimsoorts = useAppSelector(state => state.verzuimsoort.entities);
  const werknemers = useAppSelector(state => state.werknemer.entities);
  const verzuimEntity = useAppSelector(state => state.verzuim.entity);
  const loading = useAppSelector(state => state.verzuim.loading);
  const updating = useAppSelector(state => state.verzuim.updating);
  const updateSuccess = useAppSelector(state => state.verzuim.updateSuccess);

  const handleClose = () => {
    navigate('/verzuim');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVerzuimsoorts({}));
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
      ...verzuimEntity,
      ...values,
      soortverzuimVerzuimsoort: verzuimsoorts.find(it => it.id.toString() === values.soortverzuimVerzuimsoort?.toString()),
      heeftverzuimWerknemer: werknemers.find(it => it.id.toString() === values.heeftverzuimWerknemer?.toString()),
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
          ...verzuimEntity,
          soortverzuimVerzuimsoort: verzuimEntity?.soortverzuimVerzuimsoort?.id,
          heeftverzuimWerknemer: verzuimEntity?.heeftverzuimWerknemer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verzuim.home.createOrEditLabel" data-cy="VerzuimCreateUpdateHeading">
            Create or edit a Verzuim
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="verzuim-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumtijdeinde"
                id="verzuim-datumtijdeinde"
                name="datumtijdeinde"
                data-cy="datumtijdeinde"
                type="text"
              />
              <ValidatedField
                label="Datumtijdstart"
                id="verzuim-datumtijdstart"
                name="datumtijdstart"
                data-cy="datumtijdstart"
                type="text"
              />
              <ValidatedField
                id="verzuim-soortverzuimVerzuimsoort"
                name="soortverzuimVerzuimsoort"
                data-cy="soortverzuimVerzuimsoort"
                label="Soortverzuim Verzuimsoort"
                type="select"
              >
                <option value="" key="0" />
                {verzuimsoorts
                  ? verzuimsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="verzuim-heeftverzuimWerknemer"
                name="heeftverzuimWerknemer"
                data-cy="heeftverzuimWerknemer"
                label="Heeftverzuim Werknemer"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verzuim" replace color="info">
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

export default VerzuimUpdate;
