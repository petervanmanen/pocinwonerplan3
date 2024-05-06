import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Naamaanschrijvingnatuurlijkpersoon from './naamaanschrijvingnatuurlijkpersoon';
import NaamaanschrijvingnatuurlijkpersoonDetail from './naamaanschrijvingnatuurlijkpersoon-detail';
import NaamaanschrijvingnatuurlijkpersoonUpdate from './naamaanschrijvingnatuurlijkpersoon-update';
import NaamaanschrijvingnatuurlijkpersoonDeleteDialog from './naamaanschrijvingnatuurlijkpersoon-delete-dialog';

const NaamaanschrijvingnatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Naamaanschrijvingnatuurlijkpersoon />} />
    <Route path="new" element={<NaamaanschrijvingnatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<NaamaanschrijvingnatuurlijkpersoonDetail />} />
      <Route path="edit" element={<NaamaanschrijvingnatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<NaamaanschrijvingnatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NaamaanschrijvingnatuurlijkpersoonRoutes;
