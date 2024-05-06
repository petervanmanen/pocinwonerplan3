import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKlantbeoordeling } from 'app/shared/model/klantbeoordeling.model';
import { getEntities as getKlantbeoordelings } from 'app/entities/klantbeoordeling/klantbeoordeling.reducer';
import { IKlantbeoordelingreden } from 'app/shared/model/klantbeoordelingreden.model';
import { getEntity, updateEntity, createEntity, reset } from './klantbeoordelingreden.reducer';

export const KlantbeoordelingredenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const klantbeoordelings = useAppSelector(state => state.klantbeoordeling.entities);
  const klantbeoordelingredenEntity = useAppSelector(state => state.klantbeoordelingreden.entity);
  const loading = useAppSelector(state => state.klantbeoordelingreden.loading);
  const updating = useAppSelector(state => state.klantbeoordelingreden.updating);
  const updateSuccess = useAppSelector(state => state.klantbeoordelingreden.updateSuccess);

  const handleClose = () => {
    navigate('/klantbeoordelingreden');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getKlantbeoordelings({}));
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
      ...klantbeoordelingredenEntity,
      ...values,
      heeftKlantbeoordeling: klantbeoordelings.find(it => it.id.toString() === values.heeftKlantbeoordeling?.toString()),
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
          ...klantbeoordelingredenEntity,
          heeftKlantbeoordeling: klantbeoordelingredenEntity?.heeftKlantbeoordeling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.klantbeoordelingreden.home.createOrEditLabel" data-cy="KlantbeoordelingredenCreateUpdateHeading">
            Create or edit a Klantbeoordelingreden
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
                <ValidatedField name="id" required readOnly id="klantbeoordelingreden-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Reden"
                id="klantbeoordelingreden-reden"
                name="reden"
                data-cy="reden"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                id="klantbeoordelingreden-heeftKlantbeoordeling"
                name="heeftKlantbeoordeling"
                data-cy="heeftKlantbeoordeling"
                label="Heeft Klantbeoordeling"
                type="select"
              >
                <option value="" key="0" />
                {klantbeoordelings
                  ? klantbeoordelings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/klantbeoordelingreden" replace color="info">
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

export default KlantbeoordelingredenUpdate;
