import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMagazijnlocatie } from 'app/shared/model/magazijnlocatie.model';
import { getEntities as getMagazijnlocaties } from 'app/entities/magazijnlocatie/magazijnlocatie.reducer';
import { IDoos } from 'app/shared/model/doos.model';
import { getEntity, updateEntity, createEntity, reset } from './doos.reducer';

export const DoosUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const magazijnlocaties = useAppSelector(state => state.magazijnlocatie.entities);
  const doosEntity = useAppSelector(state => state.doos.entity);
  const loading = useAppSelector(state => state.doos.loading);
  const updating = useAppSelector(state => state.doos.updating);
  const updateSuccess = useAppSelector(state => state.doos.updateSuccess);

  const handleClose = () => {
    navigate('/doos');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMagazijnlocaties({}));
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
      ...doosEntity,
      ...values,
      staatopMagazijnlocatie: magazijnlocaties.find(it => it.id.toString() === values.staatopMagazijnlocatie?.toString()),
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
          ...doosEntity,
          staatopMagazijnlocatie: doosEntity?.staatopMagazijnlocatie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.doos.home.createOrEditLabel" data-cy="DoosCreateUpdateHeading">
            Create or edit a Doos
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="doos-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Doosnummer" id="doos-doosnummer" name="doosnummer" data-cy="doosnummer" type="text" />
              <ValidatedField label="Herkomst" id="doos-herkomst" name="herkomst" data-cy="herkomst" type="text" />
              <ValidatedField label="Inhoud" id="doos-inhoud" name="inhoud" data-cy="inhoud" type="text" />
              <ValidatedField label="Key" id="doos-key" name="key" data-cy="key" type="text" />
              <ValidatedField
                label="Keymagazijnlocatie"
                id="doos-keymagazijnlocatie"
                name="keymagazijnlocatie"
                data-cy="keymagazijnlocatie"
                type="text"
              />
              <ValidatedField label="Projectcd" id="doos-projectcd" name="projectcd" data-cy="projectcd" type="text" />
              <ValidatedField
                id="doos-staatopMagazijnlocatie"
                name="staatopMagazijnlocatie"
                data-cy="staatopMagazijnlocatie"
                label="Staatop Magazijnlocatie"
                type="select"
              >
                <option value="" key="0" />
                {magazijnlocaties
                  ? magazijnlocaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/doos" replace color="info">
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

export default DoosUpdate;
