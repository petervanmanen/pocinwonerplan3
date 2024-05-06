import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWerkgelegenheid } from 'app/shared/model/werkgelegenheid.model';
import { getEntities as getWerkgelegenheids } from 'app/entities/werkgelegenheid/werkgelegenheid.reducer';
import { INummeraanduiding } from 'app/shared/model/nummeraanduiding.model';
import { getEntities as getNummeraanduidings } from 'app/entities/nummeraanduiding/nummeraanduiding.reducer';
import { IVestiging } from 'app/shared/model/vestiging.model';
import { getEntity, updateEntity, createEntity, reset } from './vestiging.reducer';

export const VestigingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const werkgelegenheids = useAppSelector(state => state.werkgelegenheid.entities);
  const nummeraanduidings = useAppSelector(state => state.nummeraanduiding.entities);
  const vestigingEntity = useAppSelector(state => state.vestiging.entity);
  const loading = useAppSelector(state => state.vestiging.loading);
  const updating = useAppSelector(state => state.vestiging.updating);
  const updateSuccess = useAppSelector(state => state.vestiging.updateSuccess);

  const handleClose = () => {
    navigate('/vestiging');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getWerkgelegenheids({}));
    dispatch(getNummeraanduidings({}));
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
      ...vestigingEntity,
      ...values,
      heeftWerkgelegenheid: werkgelegenheids.find(it => it.id.toString() === values.heeftWerkgelegenheid?.toString()),
      heeftalslocatieadresNummeraanduiding: nummeraanduidings.find(
        it => it.id.toString() === values.heeftalslocatieadresNummeraanduiding?.toString(),
      ),
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
          ...vestigingEntity,
          heeftWerkgelegenheid: vestigingEntity?.heeftWerkgelegenheid?.id,
          heeftalslocatieadresNummeraanduiding: vestigingEntity?.heeftalslocatieadresNummeraanduiding?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vestiging.home.createOrEditLabel" data-cy="VestigingCreateUpdateHeading">
            Create or edit a Vestiging
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vestiging-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Commercielevestiging"
                id="vestiging-commercielevestiging"
                name="commercielevestiging"
                data-cy="commercielevestiging"
                type="text"
              />
              <ValidatedField label="Datumaanvang" id="vestiging-datumaanvang" name="datumaanvang" data-cy="datumaanvang" type="date" />
              <ValidatedField label="Datumeinde" id="vestiging-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumvoortzetting"
                id="vestiging-datumvoortzetting"
                name="datumvoortzetting"
                data-cy="datumvoortzetting"
                type="date"
              />
              <ValidatedField
                label="Fulltimewerkzamemannen"
                id="vestiging-fulltimewerkzamemannen"
                name="fulltimewerkzamemannen"
                data-cy="fulltimewerkzamemannen"
                type="text"
              />
              <ValidatedField
                label="Fulltimewerkzamevrouwen"
                id="vestiging-fulltimewerkzamevrouwen"
                name="fulltimewerkzamevrouwen"
                data-cy="fulltimewerkzamevrouwen"
                type="text"
              />
              <ValidatedField label="Handelsnaam" id="vestiging-handelsnaam" name="handelsnaam" data-cy="handelsnaam" type="text" />
              <ValidatedField
                label="Parttimewerkzamemannen"
                id="vestiging-parttimewerkzamemannen"
                name="parttimewerkzamemannen"
                data-cy="parttimewerkzamemannen"
                type="text"
              />
              <ValidatedField
                label="Parttimewerkzamevrouwen"
                id="vestiging-parttimewerkzamevrouwen"
                name="parttimewerkzamevrouwen"
                data-cy="parttimewerkzamevrouwen"
                type="text"
              />
              <ValidatedField
                label="Toevoegingadres"
                id="vestiging-toevoegingadres"
                name="toevoegingadres"
                data-cy="toevoegingadres"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Totaalwerkzamepersonen"
                id="vestiging-totaalwerkzamepersonen"
                name="totaalwerkzamepersonen"
                data-cy="totaalwerkzamepersonen"
                type="text"
              />
              <ValidatedField label="Verkortenaam" id="vestiging-verkortenaam" name="verkortenaam" data-cy="verkortenaam" type="text" />
              <ValidatedField
                label="Vestigingsnummer"
                id="vestiging-vestigingsnummer"
                name="vestigingsnummer"
                data-cy="vestigingsnummer"
                type="text"
              />
              <ValidatedField
                id="vestiging-heeftWerkgelegenheid"
                name="heeftWerkgelegenheid"
                data-cy="heeftWerkgelegenheid"
                label="Heeft Werkgelegenheid"
                type="select"
                required
              >
                <option value="" key="0" />
                {werkgelegenheids
                  ? werkgelegenheids.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="vestiging-heeftalslocatieadresNummeraanduiding"
                name="heeftalslocatieadresNummeraanduiding"
                data-cy="heeftalslocatieadresNummeraanduiding"
                label="Heeftalslocatieadres Nummeraanduiding"
                type="select"
              >
                <option value="" key="0" />
                {nummeraanduidings
                  ? nummeraanduidings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vestiging" replace color="info">
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

export default VestigingUpdate;
