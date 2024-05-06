import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBinnenlocatie } from 'app/shared/model/binnenlocatie.model';
import { getEntities as getBinnenlocaties } from 'app/entities/binnenlocatie/binnenlocatie.reducer';
import { ISportmateriaal } from 'app/shared/model/sportmateriaal.model';
import { getEntity, updateEntity, createEntity, reset } from './sportmateriaal.reducer';

export const SportmateriaalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const binnenlocaties = useAppSelector(state => state.binnenlocatie.entities);
  const sportmateriaalEntity = useAppSelector(state => state.sportmateriaal.entity);
  const loading = useAppSelector(state => state.sportmateriaal.loading);
  const updating = useAppSelector(state => state.sportmateriaal.updating);
  const updateSuccess = useAppSelector(state => state.sportmateriaal.updateSuccess);

  const handleClose = () => {
    navigate('/sportmateriaal');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBinnenlocaties({}));
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
      ...sportmateriaalEntity,
      ...values,
      heeftBinnenlocaties: mapIdList(values.heeftBinnenlocaties),
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
          ...sportmateriaalEntity,
          heeftBinnenlocaties: sportmateriaalEntity?.heeftBinnenlocaties?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.sportmateriaal.home.createOrEditLabel" data-cy="SportmateriaalCreateUpdateHeading">
            Create or edit a Sportmateriaal
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
                <ValidatedField name="id" required readOnly id="sportmateriaal-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Naam" id="sportmateriaal-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Heeft Binnenlocatie"
                id="sportmateriaal-heeftBinnenlocatie"
                data-cy="heeftBinnenlocatie"
                type="select"
                multiple
                name="heeftBinnenlocaties"
              >
                <option value="" key="0" />
                {binnenlocaties
                  ? binnenlocaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sportmateriaal" replace color="info">
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

export default SportmateriaalUpdate;
