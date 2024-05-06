import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStartkwalificatie } from 'app/shared/model/startkwalificatie.model';
import { getEntities as getStartkwalificaties } from 'app/entities/startkwalificatie/startkwalificatie.reducer';
import { ILeerling } from 'app/shared/model/leerling.model';
import { getEntity, updateEntity, createEntity, reset } from './leerling.reducer';

export const LeerlingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const startkwalificaties = useAppSelector(state => state.startkwalificatie.entities);
  const leerlingEntity = useAppSelector(state => state.leerling.entity);
  const loading = useAppSelector(state => state.leerling.loading);
  const updating = useAppSelector(state => state.leerling.updating);
  const updateSuccess = useAppSelector(state => state.leerling.updateSuccess);

  const handleClose = () => {
    navigate('/leerling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStartkwalificaties({}));
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
      ...leerlingEntity,
      ...values,
      heeftStartkwalificatie: startkwalificaties.find(it => it.id.toString() === values.heeftStartkwalificatie?.toString()),
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
          ...leerlingEntity,
          heeftStartkwalificatie: leerlingEntity?.heeftStartkwalificatie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.leerling.home.createOrEditLabel" data-cy="LeerlingCreateUpdateHeading">
            Create or edit a Leerling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="leerling-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Kwetsbarejongere"
                id="leerling-kwetsbarejongere"
                name="kwetsbarejongere"
                data-cy="kwetsbarejongere"
                check
                type="checkbox"
              />
              <ValidatedField
                id="leerling-heeftStartkwalificatie"
                name="heeftStartkwalificatie"
                data-cy="heeftStartkwalificatie"
                label="Heeft Startkwalificatie"
                type="select"
                required
              >
                <option value="" key="0" />
                {startkwalificaties
                  ? startkwalificaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/leerling" replace color="info">
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

export default LeerlingUpdate;
