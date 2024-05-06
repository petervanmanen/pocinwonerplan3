import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Naamgebruiknatuurlijkpersoon from './naamgebruiknatuurlijkpersoon';
import NaamgebruiknatuurlijkpersoonDetail from './naamgebruiknatuurlijkpersoon-detail';
import NaamgebruiknatuurlijkpersoonUpdate from './naamgebruiknatuurlijkpersoon-update';
import NaamgebruiknatuurlijkpersoonDeleteDialog from './naamgebruiknatuurlijkpersoon-delete-dialog';

const NaamgebruiknatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Naamgebruiknatuurlijkpersoon />} />
    <Route path="new" element={<NaamgebruiknatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<NaamgebruiknatuurlijkpersoonDetail />} />
      <Route path="edit" element={<NaamgebruiknatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<NaamgebruiknatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NaamgebruiknatuurlijkpersoonRoutes;
