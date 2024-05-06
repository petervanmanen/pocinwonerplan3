import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRaadsstuk } from 'app/shared/model/raadsstuk.model';
import { getEntities as getRaadsstuks } from 'app/entities/raadsstuk/raadsstuk.reducer';
import { IRaadscommissie } from 'app/shared/model/raadscommissie.model';
import { getEntities as getRaadscommissies } from 'app/entities/raadscommissie/raadscommissie.reducer';
import { IVergadering } from 'app/shared/model/vergadering.model';
import { getEntity, updateEntity, createEntity, reset } from './vergadering.reducer';

export const VergaderingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const raadsstuks = useAppSelector(state => state.raadsstuk.entities);
  const raadscommissies = useAppSelector(state => state.raadscommissie.entities);
  const vergaderingEntity = useAppSelector(state => state.vergadering.entity);
  const loading = useAppSelector(state => state.vergadering.loading);
  const updating = useAppSelector(state => state.vergadering.updating);
  const updateSuccess = useAppSelector(state => state.vergadering.updateSuccess);

  const handleClose = () => {
    navigate('/vergadering');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRaadsstuks({}));
    dispatch(getRaadscommissies({}));
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
      ...vergaderingEntity,
      ...values,
      heeftverslagRaadsstuk: raadsstuks.find(it => it.id.toString() === values.heeftverslagRaadsstuk?.toString()),
      heeftRaadscommissie: raadscommissies.find(it => it.id.toString() === values.heeftRaadscommissie?.toString()),
      wordtbehandeldinRaadsstuks: mapIdList(values.wordtbehandeldinRaadsstuks),
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
          ...vergaderingEntity,
          heeftverslagRaadsstuk: vergaderingEntity?.heeftverslagRaadsstuk?.id,
          heeftRaadscommissie: vergaderingEntity?.heeftRaadscommissie?.id,
          wordtbehandeldinRaadsstuks: vergaderingEntity?.wordtbehandeldinRaadsstuks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vergadering.home.createOrEditLabel" data-cy="VergaderingCreateUpdateHeading">
            Create or edit a Vergadering
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vergadering-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Eindtijd" id="vergadering-eindtijd" name="eindtijd" data-cy="eindtijd" type="text" />
              <ValidatedField label="Locatie" id="vergadering-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField label="Starttijd" id="vergadering-starttijd" name="starttijd" data-cy="starttijd" type="text" />
              <ValidatedField label="Titel" id="vergadering-titel" name="titel" data-cy="titel" type="text" />
              <ValidatedField
                id="vergadering-heeftverslagRaadsstuk"
                name="heeftverslagRaadsstuk"
                data-cy="heeftverslagRaadsstuk"
                label="Heeftverslag Raadsstuk"
                type="select"
              >
                <option value="" key="0" />
                {raadsstuks
                  ? raadsstuks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="vergadering-heeftRaadscommissie"
                name="heeftRaadscommissie"
                data-cy="heeftRaadscommissie"
                label="Heeft Raadscommissie"
                type="select"
              >
                <option value="" key="0" />
                {raadscommissies
                  ? raadscommissies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Wordtbehandeldin Raadsstuk"
                id="vergadering-wordtbehandeldinRaadsstuk"
                data-cy="wordtbehandeldinRaadsstuk"
                type="select"
                multiple
                name="wordtbehandeldinRaadsstuks"
              >
                <option value="" key="0" />
                {raadsstuks
                  ? raadsstuks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vergadering" replace color="info">
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

export default VergaderingUpdate;
