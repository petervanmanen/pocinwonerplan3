import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKast } from 'app/shared/model/kast.model';
import { getEntities as getKasts } from 'app/entities/kast/kast.reducer';
import { IPlank } from 'app/shared/model/plank.model';
import { getEntity, updateEntity, createEntity, reset } from './plank.reducer';

export const PlankUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kasts = useAppSelector(state => state.kast.entities);
  const plankEntity = useAppSelector(state => state.plank.entity);
  const loading = useAppSelector(state => state.plank.loading);
  const updating = useAppSelector(state => state.plank.updating);
  const updateSuccess = useAppSelector(state => state.plank.updateSuccess);

  const handleClose = () => {
    navigate('/plank');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getKasts({}));
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
      ...plankEntity,
      ...values,
      heeftKast: kasts.find(it => it.id.toString() === values.heeftKast?.toString()),
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
          ...plankEntity,
          heeftKast: plankEntity?.heeftKast?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.plank.home.createOrEditLabel" data-cy="PlankCreateUpdateHeading">
            Create or edit a Plank
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="plank-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Planknummer"
                id="plank-planknummer"
                name="planknummer"
                data-cy="planknummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField id="plank-heeftKast" name="heeftKast" data-cy="heeftKast" label="Heeft Kast" type="select">
                <option value="" key="0" />
                {kasts
                  ? kasts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plank" replace color="info">
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

export default PlankUpdate;
