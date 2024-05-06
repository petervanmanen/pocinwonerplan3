import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStelling } from 'app/shared/model/stelling.model';
import { getEntities as getStellings } from 'app/entities/stelling/stelling.reducer';
import { IMagazijnlocatie } from 'app/shared/model/magazijnlocatie.model';
import { getEntity, updateEntity, createEntity, reset } from './magazijnlocatie.reducer';

export const MagazijnlocatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const stellings = useAppSelector(state => state.stelling.entities);
  const magazijnlocatieEntity = useAppSelector(state => state.magazijnlocatie.entity);
  const loading = useAppSelector(state => state.magazijnlocatie.loading);
  const updating = useAppSelector(state => state.magazijnlocatie.updating);
  const updateSuccess = useAppSelector(state => state.magazijnlocatie.updateSuccess);

  const handleClose = () => {
    navigate('/magazijnlocatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStellings({}));
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
      ...magazijnlocatieEntity,
      ...values,
      heeftStelling: stellings.find(it => it.id.toString() === values.heeftStelling?.toString()),
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
          ...magazijnlocatieEntity,
          heeftStelling: magazijnlocatieEntity?.heeftStelling?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.magazijnlocatie.home.createOrEditLabel" data-cy="MagazijnlocatieCreateUpdateHeading">
            Create or edit a Magazijnlocatie
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
                <ValidatedField name="id" required readOnly id="magazijnlocatie-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Key"
                id="magazijnlocatie-key"
                name="key"
                data-cy="key"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Vaknummer"
                id="magazijnlocatie-vaknummer"
                name="vaknummer"
                data-cy="vaknummer"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                label="Volgletter"
                id="magazijnlocatie-volgletter"
                name="volgletter"
                data-cy="volgletter"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                id="magazijnlocatie-heeftStelling"
                name="heeftStelling"
                data-cy="heeftStelling"
                label="Heeft Stelling"
                type="select"
              >
                <option value="" key="0" />
                {stellings
                  ? stellings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/magazijnlocatie" replace color="info">
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

export default MagazijnlocatieUpdate;
