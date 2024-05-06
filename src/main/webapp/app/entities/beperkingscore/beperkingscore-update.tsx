import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBeperkingscoresoort } from 'app/shared/model/beperkingscoresoort.model';
import { getEntities as getBeperkingscoresoorts } from 'app/entities/beperkingscoresoort/beperkingscoresoort.reducer';
import { IBeperking } from 'app/shared/model/beperking.model';
import { getEntities as getBeperkings } from 'app/entities/beperking/beperking.reducer';
import { IBeperkingscore } from 'app/shared/model/beperkingscore.model';
import { getEntity, updateEntity, createEntity, reset } from './beperkingscore.reducer';

export const BeperkingscoreUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const beperkingscoresoorts = useAppSelector(state => state.beperkingscoresoort.entities);
  const beperkings = useAppSelector(state => state.beperking.entities);
  const beperkingscoreEntity = useAppSelector(state => state.beperkingscore.entity);
  const loading = useAppSelector(state => state.beperkingscore.loading);
  const updating = useAppSelector(state => state.beperkingscore.updating);
  const updateSuccess = useAppSelector(state => state.beperkingscore.updateSuccess);

  const handleClose = () => {
    navigate('/beperkingscore');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBeperkingscoresoorts({}));
    dispatch(getBeperkings({}));
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
      ...beperkingscoreEntity,
      ...values,
      iseenBeperkingscoresoort: beperkingscoresoorts.find(it => it.id.toString() === values.iseenBeperkingscoresoort?.toString()),
      emptyBeperking: beperkings.find(it => it.id.toString() === values.emptyBeperking?.toString()),
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
          ...beperkingscoreEntity,
          iseenBeperkingscoresoort: beperkingscoreEntity?.iseenBeperkingscoresoort?.id,
          emptyBeperking: beperkingscoreEntity?.emptyBeperking?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.beperkingscore.home.createOrEditLabel" data-cy="BeperkingscoreCreateUpdateHeading">
            Create or edit a Beperkingscore
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
                <ValidatedField name="id" required readOnly id="beperkingscore-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Commentaar" id="beperkingscore-commentaar" name="commentaar" data-cy="commentaar" type="text" />
              <ValidatedField label="Score" id="beperkingscore-score" name="score" data-cy="score" type="text" />
              <ValidatedField label="Wet" id="beperkingscore-wet" name="wet" data-cy="wet" type="text" />
              <ValidatedField
                id="beperkingscore-iseenBeperkingscoresoort"
                name="iseenBeperkingscoresoort"
                data-cy="iseenBeperkingscoresoort"
                label="Iseen Beperkingscoresoort"
                type="select"
              >
                <option value="" key="0" />
                {beperkingscoresoorts
                  ? beperkingscoresoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="beperkingscore-emptyBeperking"
                name="emptyBeperking"
                data-cy="emptyBeperking"
                label="Empty Beperking"
                type="select"
              >
                <option value="" key="0" />
                {beperkings
                  ? beperkings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/beperkingscore" replace color="info">
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

export default BeperkingscoreUpdate;
