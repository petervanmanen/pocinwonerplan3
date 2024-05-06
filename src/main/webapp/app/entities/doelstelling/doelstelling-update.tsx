import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDoelstellingsoort } from 'app/shared/model/doelstellingsoort.model';
import { getEntities as getDoelstellingsoorts } from 'app/entities/doelstellingsoort/doelstellingsoort.reducer';
import { IHoofdstuk } from 'app/shared/model/hoofdstuk.model';
import { getEntities as getHoofdstuks } from 'app/entities/hoofdstuk/hoofdstuk.reducer';
import { IDoelstelling } from 'app/shared/model/doelstelling.model';
import { getEntity, updateEntity, createEntity, reset } from './doelstelling.reducer';

export const DoelstellingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const doelstellingsoorts = useAppSelector(state => state.doelstellingsoort.entities);
  const hoofdstuks = useAppSelector(state => state.hoofdstuk.entities);
  const doelstellingEntity = useAppSelector(state => state.doelstelling.entity);
  const loading = useAppSelector(state => state.doelstelling.loading);
  const updating = useAppSelector(state => state.doelstelling.updating);
  const updateSuccess = useAppSelector(state => state.doelstelling.updateSuccess);

  const handleClose = () => {
    navigate('/doelstelling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDoelstellingsoorts({}));
    dispatch(getHoofdstuks({}));
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
      ...doelstellingEntity,
      ...values,
      isvansoortDoelstellingsoort: doelstellingsoorts.find(it => it.id.toString() === values.isvansoortDoelstellingsoort?.toString()),
      heeftHoofdstuk: hoofdstuks.find(it => it.id.toString() === values.heeftHoofdstuk?.toString()),
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
          ...doelstellingEntity,
          isvansoortDoelstellingsoort: doelstellingEntity?.isvansoortDoelstellingsoort?.id,
          heeftHoofdstuk: doelstellingEntity?.heeftHoofdstuk?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.doelstelling.home.createOrEditLabel" data-cy="DoelstellingCreateUpdateHeading">
            Create or edit a Doelstelling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="doelstelling-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Omschrijving" id="doelstelling-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                id="doelstelling-isvansoortDoelstellingsoort"
                name="isvansoortDoelstellingsoort"
                data-cy="isvansoortDoelstellingsoort"
                label="Isvansoort Doelstellingsoort"
                type="select"
              >
                <option value="" key="0" />
                {doelstellingsoorts
                  ? doelstellingsoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="doelstelling-heeftHoofdstuk"
                name="heeftHoofdstuk"
                data-cy="heeftHoofdstuk"
                label="Heeft Hoofdstuk"
                type="select"
              >
                <option value="" key="0" />
                {hoofdstuks
                  ? hoofdstuks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/doelstelling" replace color="info">
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

export default DoelstellingUpdate;
